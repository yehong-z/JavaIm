package connect

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
)

func Login(username, password string) (string, error) {
	url := "http://127.0.0.1:8080/login"
	method := "POST"
	fmt.Printf("%v,%v", username, password)
	jsonBody, _ := json.Marshal(&LoginReq{"zyh", "zyh"})
	payload := strings.NewReader(string(jsonBody))

	fmt.Println(string(jsonBody))
	client := &http.Client{}
	req, err := http.NewRequest(method, url, payload)
	req.Header.Add("Content-Type", "application/json")
	if err != nil {
		return "", err
	}

	res, err := client.Do(req)
	if err != nil {
		return "", err
	}
	defer res.Body.Close()

	body, err := ioutil.ReadAll(res.Body)
	if err != nil {
		return "", err
	}

	return string(body), nil
}
