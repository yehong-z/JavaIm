package connect

type Resp struct {
	Code    string    `json:"code"`
	Message string    `json:"message"`
	Data    LoginResp `json:"data"`
}
