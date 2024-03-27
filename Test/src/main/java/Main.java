import com.alibaba.fastjson.JSON;
import com.zyh.javaim.Message;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Message message = new Message();
        message.setFromUserId(1L).setType(1).setContent("111");
        String json = JSON.toJSONString(message);
        System.out.println(json);

        // JSON字符串转换为Java对象
        Message newObj = JSON.parseObject(json, Message.class);
        System.out.println(newObj.toString());

    }
}
