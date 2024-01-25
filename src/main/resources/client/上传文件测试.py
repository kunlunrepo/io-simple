# -*- coding: utf-8 -*-


import socket, os

# 创建一个socket对象
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 连接服务器
s.connect(('127.0.0.1', 9001))

# 文件路径
file_path = 'U://test.zip'
# 判断是否是文件
if os.path.isfile(file_path):
    # 文件名
    filename = os.path.basename(file_path).encode('utf-8')

    # 创建文件
    command = 1

    # 文件名长度
    file_name_len = len(filename)
    # 函数可以用来创建一个字节对象
    file_name_data = bytes(filename)
    # 将这个整数值转换为一个固定长度（这里是4个字节）的字节序列，并采用大端（Big-endian）字节序
    i = file_name_len.to_bytes(4, 'big')
    c = command.to_bytes(4, 'big')

    # 发送文件名长度和文件名
    s.sendall(c + i + file_name_data)

    # 打开文件
    fo = open(file_path, 'rb')
    while True:
        # 写入文件
        command = 2;
        c = command.to_bytes(4, byteorder='big')
        filedata = fo.read(1024)
        print(len(filedata))
        b = len(filedata).to_bytes(4, byteorder='big')
        if not filedata:
            break
        s.sendall(c + i + file_name_data + b + filedata)
    fo.close()
else:
    print('文件不存在')