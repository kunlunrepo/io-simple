
import socket

# 创建一个socket对象
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 连接服务器
s.connect(('127.0.0.1', 9001))

# 循环发送100条消息
for i in range(100):
    # 打印第几次发送
    print('第%d次发送消息' % i)
    # 创建消息
    str = 'hello world %d' % i
    # 将消息转换为bytes类型
    body = bytes(str, encoding='utf-8')
    # 发送消息
    s.sendall(body)