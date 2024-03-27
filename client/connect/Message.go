package connect

type Message struct {
	MsgSeq   int64  `json:"msgSeq"`
	Type     int    `json:"type"`
	FromUser int64  `json:"fromUserId"`
	ToUser   int64  `json:"toUserId"`
	Content  string `json:"content"`
}
