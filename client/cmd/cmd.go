package cmd

import (
	"context"
	"encoding/json"
	"fmt"
	"imClient/connect"
	"os"
	"time"

	"github.com/spf13/cobra"
)

var Token string

var rootCmd = &cobra.Command{
	Use:   "hugo",
	Short: "Hugo is a very fast static site generator",
	Long: `A Fast and Flexible Static Site Generator built with
                love by spf13 and friends in Go.
                Complete documentation is available at https://gohugo.io`,
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Printf("username:")

		ret, err := connect.Login("username", "password")
		if err != nil {
			fmt.Println(err.Error())
		}

		fmt.Println(ret)
		Resp := &connect.Resp{
			Data: connect.LoginResp{},
		}
		err = json.Unmarshal([]byte(ret), Resp)
		if err != nil {
			fmt.Println(err.Error())
		}

		fmt.Println(Resp.Data.Token)
		Token = Resp.Data.Token
		ctx, cancel := context.WithCancel(context.Background())
		ch := make(chan connect.Message, 10)
		conn := connect.ConnectTcp(ctx, Token, ch)
		//Msg := connect.Message{
		//	Type:     6,
		//	FromUser: int64(1),
		//	ToUser:   int64(2),
		//	Content:  "123123",
		//}
		//ch <- Msg
		time.Sleep(10 * time.Second)
		cancel()
		conn.Close()
	},
}

func Execute() {
	if err := rootCmd.Execute(); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}
