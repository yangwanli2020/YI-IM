
let IM_WS = {
    params: {},             //  socket参数
    uri:'ws://localhost:9901',
    state: 'disconnect',
    defaultImSocket: null,
    /**
     *  初始化默认websocket连接
     * @param uId
     * @param uName
     */
    initDefaultImSocket: function(userId, userName) {
        let that = this;
        this.params = {
            query : 'userId='+userId+'&userName='+userName+'&sockettype=im'
        }
        this.defaultImSocket = io.connect(this.uri+'/default', this.params);
        this.defaultImSocket.on(IM_EVENT.CONNECT_EVENT, function () {

            //  连接成功1s后，将用户信息注册到服务器
            setTimeout(function() {
                that.register();
            }, 1000)
        });
        this.defaultImSocket.on(IM_EVENT.DISCONNECTED_EVENT, function () {
            this.state = IM_EVENT.DISCONNECTED_EVENT;
        });

    },
}

/**
 * socket事件
 */
const IM_EVENT = {
    REGISTER_EVENT: "REGISTER",               //  用户注册事件
    JOIN_ROOM_EVENT: "JOIN_ROOM",             //  用户加入房间事件
    CONNECT_EVENT: "connect",                 //  连接事件
    DISCONNECTED_EVENT: "disconnected",       //  连接断开事件
    SEND_MSG_EVENT: "SEND_MSG",               //  发送消息事件
}
var IM_SOCKET = function(_callback) {
    var me = this;
    this.config = {
        params: {},
        uri:'ws://localhost:9901',
        state: 'disconnect',
        defaultImSocket: null,
        namespace: "/default",
        clientId: randomNum(1, 10) + '-' + randomNum(1, 20) + '-' + randomNum(1, 30),
        roomId: "default"
    }



    // socket对象
    this.socket = null;

    // 初始化连接
    this.init = function () {
        me.config.params = {
            query: 'clientId='+me.config.clientId
        }
        me.socket = io.connect(me.config.uri+me.config.namespace, me.config.params);
        me.on(IM_EVENT.CONNECT_EVENT, function () {
            //  连接成功1s后，将用户信息注册到服务器
            setTimeout(function() {
                me.register();
            }, 1000)
        })
        me.on(IM_EVENT.DISCONNECTED_EVENT, function () {
            this.state = 'disconnect';
        });
    }

    // 绑定某个事件 动态绑定支持后续二次开发
    this.on = function (eventName, callback) {
        me.socket.on(eventName, function (data, ackServerCallback) {
            if(Boolean(callback)) {
                callback(data)
            }
            if (ackServerCallback) {
                ackServerCallback();
            }
        });
    }

    // 回调服务器某个事件
    this.emit = function (eventName, p, callback) {
        me.socket.emit(eventName, p, callback);
    }

    // 发送消息
    // 支持socket 与  api方式
    this.send = function (data, callback) {
        if(!data.hasOwnProperty('roomId') || !Boolean(data.roomId)){
            data.roomId = me.config.roomId;
        }
        data.clientId = me.config.clientId;
        me.emit(IM_EVENT.SEND_MSG_EVENT, data, function(arg1, arg2) {
            if(Boolean(callback)) {
                callback(arg2)
            }
        });
    }

    // 将连接注册到服务器
    this.register = function() {
        me.emit(IM_EVENT.REGISTER_EVENT, {
            '@class': 'com.itic.im.core.model.Online',
            clientId: me.config.clientId
        }, function(arg1, arg2) {
            me.config.state = 'connect';
            // alert("ack from server! arg1: " + arg1 + ", arg2: " + arg2);
            // me.joinRoom(me.config.roomId)
            if(Boolean(_callback)) {
                _callback(arg2);
            }
        });
    }

    // 关闭连接
    this.close = function() {
        me.socket.close();
        me.state = 'disconnect';
    }

    // 用户加入指定房间
    this.joinRoom = function (r, callback) {
        me.socket.emit(IM_EVENT.JOIN_ROOM_EVENT, {
            clientId: me.config.clientId,
            roomId: r
        }, function(arg1,arg2) {
            if(Boolean(callback)) {
                callback(arg2)
            }
        });
    }

    this.init();
    return this;
}

//生成从minNum到maxNum的随机数
function randomNum(minNum,maxNum){
    switch(arguments.length){
        case 1:
            return parseInt(Math.random()*minNum+1,10);
            break;
        case 2:
            return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10);
            break;
        default:
            return 0;
            break;
    }
}
