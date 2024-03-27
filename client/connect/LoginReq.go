package connect

type LoginReq struct {
	Username string `json:"userName"`
	Password string `json:"password"`
}

type LoginResp struct {
	Token string `json:"token"`
}
