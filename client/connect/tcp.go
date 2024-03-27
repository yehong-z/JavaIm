package connect

import (
	"context"
	"encoding/json"
	"fmt"
	"net"
	"time"
)

func ConnectTcp(ctx context.Context, token string, ch chan Message) net.Conn {
	conn, err := net.Dial("tcp", "127.0.0.1:8088")
	if err != nil {
		fmt.Println("无法连接到服务器:", err)
		return nil
	}

	fmt.Println("成功连接到服务器")

	//不断读数据
	go func(ctx context.Context) {
		// 示例：从服务器接收数据
		for {
			select {
			case <-ctx.Done():
				return
			default:
				buffer := make([]byte, 1024)
				n, err := conn.Read(buffer)
				if err != nil {
					fmt.Println("从服务器接收数据失败:", err)
					return
				}
				fmt.Println("从服务器接收的数据:", string(buffer[:n]))
			}

		}

	}(ctx)

	// 登录
	authMsg, _ := json.Marshal(&Message{
		Type:    4,
		Content: token,
	})
	conn.Write(authMsg)

	go func(ctx context.Context) {
		for {
			select {
			case <-ctx.Done():
				return
			case <-time.Tick(time.Second * 2):
				fmt.Println("send heart beat")
				heatbeatMsg := Message{
					Type: 6,
				}
				hb, _ := json.Marshal(&heatbeatMsg)
				conn.Write(hb)
			case msg := <-ch:
				m, _ := json.Marshal(&msg)
				conn.Write(m)
			}
		}

	}(ctx)
	// 控制数据发送和心跳
	return conn
}
