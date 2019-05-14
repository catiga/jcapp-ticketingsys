# 售票系统对接应用
------
## 说明 | 准备工作
> 本应用APPCODE为 ticketingsys 即调用凭证  
> 联系对应售票系统提供商开具渠道  
> 准备好对应影城的影城编码（国家统一发放）  
> 如有疑问可Email至 zhang_gh@cpis.cn  
> 微信扫码捐赠  
> ![](http://7xsy9q.com1.z0.glb.clouddn.com/WX20180703-132251@2x.png?imageView2/2/w/300)  

## 一、售票系统支持列表
* 鼎鑫
* 火烈鸟
* 晨星
* 满天星

## 二、系统使用说明
> ### 添加售票系统  
>> 对应售票系统提供商开具好渠道后，会提供接口所需的调用地址，渠道号，渠道秘钥  
>> 左侧菜单 “售票系统” 按钮 “添加售票系统” 填好对应的信息，选择正确的售票系统提供商  

> ### 添加影城  
>> 添加好售票系统后   
>> 左侧菜单 “影城管理” 按钮 “添加影城” 填写正确的影城编码，选择对应的售票系统，先点验证  
>> 验证通过后 完善好省市区详细地址后 将影城添加到系统内  

## 三、其他APP对接说明
### 1、基本响应数据体
```javascript
{
	code: 0,//错误代码 参考返回结果代码说明
	msg: null,//错误说明
	data: //响应数据体
}
```
### 2、对接接口说明
#### 获取某影城某天的排期情况
接口地址
> /plan/movies

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>cinema_id</td><td>Long</td><td>获取影城列表中返回的影城id</td></tr>
	<tr><td>day</td><td>String</td><td>yyyy-MM-dd格式的日期</td></tr>
	<tr><td>sortby</td><td>String</td><td>排序规则 按时间date或者按热度hot</td></tr>
</table>

返回结果
```javascript
{
    "code":0,
    "msg":null,
    "data":[
        {
            "name":"指环王3：王者无敌",
            "img":"http://p0.meituan.net/180.250/movie/932bdfbef5be3543e6b136246aeb99b8123736.jpg",
            "score":"9.2",
            "dates":[
                {
                    "date":"2018-08-04",
                    "aliasName":"今天",
                    "plans":[
                        {
                            "cinemaId":"7",
                            "planId":"314643",
                            "startTime":"2018-08-04 12:35:00",
                            "endTime":"2018-08-04 14:35:00",
                            "subtitle":"中文",
                            "language":"英语",
                            "format":"数字",
                            "hallId":"401",
                            "hall":"奔驰IMAX厅",
                            "dimensional":"2D",
                            "size":"普通",
                            "lastUpdateTime":"2018-07-30 11:37:51",
                            "schemas":[
                                {
                                    "id":9,
                                    "item_id":8,
                                    "group_id":4,
                                    "schema_name":"2D30",
                                    "sort_num":0,
                                    "schema_status":"00",
                                    "by_permission":false,
                                    "week_rule":"1,2,3,4,5,6,7",
                                    "time_rule":"00:00,23:59",
                                    "month_rule":"-",
                                    "a_time":1532524465000,
                                    "price":30,
                                    "is_custom": true//是否为收银员自定义价格
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
}
```

调用示例

```java
//适用groovy sdk
//整理参数
List<CommunicationParam> params = new ArrayList<CommunicationParam>();
params.add(new CommunicationParam("cinema_id",5));
params.add(new CommunicationParam("day","2018-08-04"));
//获取app调用句柄 本应用appcode为ticketingsys
CommunicationPower caller = CommunicationSource.getCommunicator("ticketingsys");
caller.doworkAsString("/plan/movies", params);
```


#### 获取某排期座位
接口地址
> /plan/seats

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>cinema_id</td><td>Long</td><td>获取影城列表中返回的影城id</td></tr>
	<tr><td>plan_id</td><td>String</td><td>排期id</td></tr>
	<tr><td>last_update_time</td><td>String</td><td>排期最后更新时间</td></tr>
	<tr><td>hall_id</td><td>String</td><td>影厅id</td></tr>
</table>

返回结果
```javascript
{
    "code":0,
    "msg":null,
    "data":{
        "big_col":17,
        "big_row":13,
        "all_row":13,
        "left_seat_num":210,
        "sold_seat_num":11,
        "rows":[
            {
                "row":"1",
                "seats":[
                    {
                        "seatNo": "16590",
                        "seatPieceNo": "3",
                        "graphRow": "1",
                        "graphCol": "4",
                        "seatRow": "1",
                        "seatCol": "4",
                        "seatState": "6",//4可预订 2已锁定 0已售出 6已在本平台预订 8已在本平台售出
                        "cineSeatId": "16590",
                        "cinemaId": "11",
                        "xCoord": 1,
                        "yCoord": 4,
                        "loveseats": "",
                        "row": "1",
                        "column": "4",
                        "status": "ok",
                        "type": "danren",
                        "area_no": null,
                        "reserveOrder": {
                            "id": 5,
                            "order_no": "20180809113218025704",
                            "a_time": 1533785539000,
                            "ticket_sum": 1,
                            "plan_date": "2018-08-09",
                            "plan_time": "14:10:00",
                            "store_name": "测试-81",
                            "hall_name": "看看到底几个字大厅",
                            "film_name": "拯救大明星",
                            "total_amount": "180.00"
                        },
                        "buyOrder":null
                    },
                    {
                        "seatNo": "16587",
                        "seatPieceNo": "3",
                        "graphRow": "1",
                        "graphCol": "1",
                        "seatRow": "1",
                        "seatCol": "1",
                        "seatState": "8",
                        "cineSeatId": "16587",
                        "cinemaId": "11",
                        "xCoord": 1,
                        "yCoord": 1,
                        "loveseats": "",
                        "row": "1",
                        "column": "1",
                        "status": "ok",
                        "type": "danren",
                        "area_no": null,
                        "reserveOrder": null,
                        "buyOrder": {//如果是本平台售出的 此处会有信息
                            "id": 15,
                            "order_no": "2018080911563279910",
                            "a_time": 1533786993000,
                            "ticket_sum": 1,
                            "plan_date": "2018-08-09",
                            "plan_time": "14:10:00",
                            "store_name": "测试-81",
                            "hall_name": "看看到底几个字大厅",
                            "film_name": "拯救大明星",
                            "total_amount": "180.00"
                        },
                        "lockOrder": {//如果是本平台锁定的 此处会有信息
                            "id": 15,
                            "order_no": "2018080911563279910",
                            "a_time": 1533786993000,
                            "ticket_sum": 1,
                            "plan_date": "2018-08-09",
                            "plan_time": "14:10:00",
                            "store_name": "测试-81",
                            "hall_name": "看看到底几个字大厅",
                            "film_name": "拯救大明星",
                            "total_amount": "180.00"
                        },
                        "prelockOrder": {//如果有长时间锁定订单 此处会有信息
                            "id": 8,
                            "order_no": "20180817131642254662",
                            "a_time": 1534483003000,
                            "ticket_sum": 4,
                            "plan_date": "2018-08-17",
                            "plan_time": "14:30:00",
                            "store_name": "测试-81",
                            "hall_name": "奔驰IMAX厅",
                            "film_name": "拯救大明星",
                            "total_amount": "240.00"
                        }
                    },
                ]
            }
        ]
    }
}
```

调用示例

```java
略
```



#### 锁座创建订单
接口地址
> /plan/genorder

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>cinema_id</td><td>Long</td><td>获取影城列表中返回的影城id</td></tr>
	<tr><td>plan_id</td><td>String</td><td>排期id</td></tr>
	<tr><td>plan_date</td><td>String</td><td>排期日期 格式：2018-05-05</td></tr>
	<tr><td>last_update_time</td><td>String</td><td>排期最后更新时间</td></tr>
	<tr><td>seat_ids</td><td>String</td><td>所选座位协议字符串[{"seat_id":123,"schema_item_id":456,"custom_price":80}]</td></tr>
	<tr><td>phone_number</td><td>String</td><td>下单或预约人手机号码</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "id": 25,
        "order_no": "20180810115755912215",
        "mobile": null,
        "order_status": "0000",
        "total_amount": "12000.00",
        "pay_amount": null,
        "pay_time": null,
        "check_time": null,
        "deliver_time": null,
        "ticket_sum": 1,
        "store_id": 7,
        "store_name": "测试-81",
        "hall_id": "401",
        "hall_name": "奔驰IMAX厅",
        "plan_id": "314925",
        "plan_date": "2018-08-10",
        "plan_time": "12:35:00",
        "film_name": "指环王3王者归来",
        "pic_url": null,
        "pay_type": null,
        "o_c": null,
        "handle_fee": null,
        "drawback_time": null,
        "refund_time": null,
        "remark": null,
        "seats": [
            {
                "id": null,
                "order_id": 25,
                "seat_no": "9170",
                "seat_gr": 2,
                "seat_gc": 3,
                "seat_sr": "2",
                "seat_sc": "3",
                "handle_fee": "0",
                "sale_fee": "12000.00",
                "pub_fee": "12000.00"
            }
        ]
    }
}
```

调用示例

```java
略
```


#### 出票
接口地址
> /plan/pub

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>order_no</td><td>String</td><td>创建订单返回的订单编号</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "code": "0",
        "msg": "success",
        "rmCode": null,
        "rmMsg": null,
        "value1": "222551",//取票码
        "value2": "345727",//验证码
        "ticket_nos": [
            "16148",
            "1000001000036413",
            "16149",
            "1000001000036414"
        ],
        "success": true
    }
}
```

调用示例

```java
略
```

#### 退票
接口地址
> /plan/reforder

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>order_no</td><td>String</td><td>创建订单返回的订单编号</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "code": "0",
        "msg": "success",
        "rmCode": null,
        "rmMsg": null,
        "result": [
            {
                "seatId": "16148",
                "refundMoney": "180.00",
                "refundCode": "5676",
                "refundTime": "2018-08-05 16:40:03"
            },
            {
                "seatId": "16149",
                "refundMoney": "180.00",
                "refundCode": "5677",
                "refundTime": "2018-08-05 16:40:03"
            }
        ],
        "goods_refunds": [],
        "success": true
    }
}
```

调用示例

```java
略
```

#### 退票 部分座位 每笔订单仅限操作一次
接口地址
> /plan/reforderseats

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>order_no</td><td>String</td><td>创建订单返回的订单编号</td></tr>
	<tr><td>seat_ids</td><td>String</td><td>单个座位no或者是多个以逗号分隔</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "code": "0",
        "msg": "success",
        "rmCode": null,
        "rmMsg": null,
        "result": [
            {
                "seatId": "16148",
                "refundMoney": "180.00",
                "refundCode": "5676",
                "refundTime": "2018-08-05 16:40:03"
            },
            {
                "seatId": "16149",
                "refundMoney": "180.00",
                "refundCode": "5677",
                "refundTime": "2018-08-05 16:40:03"
            }
        ],
        "goods_refunds": [],
        "success": true
    }
}
```

调用示例

```java
略
```

#### 解锁
接口地址
> /plan/unlock

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>order_no</td><td>String</td><td>创建订单返回的订单编号</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": true
}
```

调用示例

```java
略
```

#### 预约
接口地址
> /plan/genreserve

```java
参考锁座下单
```

#### 取消预约
接口地址
> /plan/refreserve

```java
参考退票
```

#### 取消预约 部分座位
接口地址
> /plan/refreserveseats

```java
参考退票 部分座位
```

#### 锁座
接口地址
> /plan/genlock

```java
参考锁座下单
```

#### 取消锁座
接口地址
> /plan/reflock

```java
参考退票
```

#### 取消锁座 部分座位
接口地址
> /plan/reflockseats

```java
参考退票 部分座位
```

#### 订单列表
接口地址
> /plan/orders

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>必须</th><th>说明</th></tr>
	<tr><td>pn</td><td>int</td><td>否</td><td>页码</td></tr>
	<tr><td>ps</td><td>int</td><td>否</td><td>每页条数</td></tr>
	<tr><td>phone_number</td><td>int</td><td>否</td><td>手机号条件</td></tr>
	<tr><td>order_no</td><td>int</td><td>否</td><td>订单号条件</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": [
        {
            "id": 15,
            "order_no": "2018080911563279910",
            "mobile": null,
            "order_status": "2000",
            "total_amount": "18000.00",
            "pay_amount": null,
            "pay_time": null,
            "check_time": null,
            "deliver_time": null,
            "ticket_sum": 1,
            "store_id": 7,
            "store_name": "测试-81",
            "hall_id": "603",
            "hall_name": "看看到底几个字大厅",
            "plan_id": "314883",
            "plan_date": "2018-08-09",
            "plan_time": "14:10:00",
            "film_name": "拯救大明星",
            "pic_url": null,
            "pay_type": "0000",
            "o_c": "2000",
            "handle_fee": "0",
            "drawback_time": null,
            "refund_time": null,
            "remark": null,
            "seats": [
                {
                    "id": 14,
                    "order_id": 15,
                    "seat_no": "16587",
                    "seat_gr": 1,
                    "seat_gc": 1,
                    "seat_sr": "1",
                    "seat_sc": "1",
                    "handle_fee": "0",
                    "sale_fee": "18000.00",
                    "pub_fee": "18000.00"
                }
            ]
        }
    ]
}
```

调用示例

```java
略
```

#### 预约列表
接口地址
> /plan/reserves

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>必须</th><th>说明</th></tr>
	<tr><td>pn</td><td>int</td><td>否</td><td>页码</td></tr>
	<tr><td>ps</td><td>int</td><td>否</td><td>每页条数</td></tr>
	<tr><td>phone_number</td><td>int</td><td>否</td><td>手机号条件</td></tr>
	<tr><td>order_no</td><td>int</td><td>否</td><td>订单号条件</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": [
        {
            "id": 9,
            "order_no": "2018081016092669510",
            "mobile": null,
            "order_status": "0000",
            "total_amount": "10500.00",
            "pay_amount": null,
            "pay_time": null,
            "check_time": null,
            "deliver_time": null,
            "ticket_sum": 5,
            "store_id": 7,
            "store_name": "测试-81",
            "hall_id": "400",
            "hall_name": "一号厅",
            "plan_id": "314945",
            "plan_date": "2018-08-10",
            "plan_time": "20:35:00",
            "film_name": "指环王3王者归来",
            "pic_url": null,
            "pay_type": "0000",
            "o_c": "2000",
            "handle_fee": "0",
            "drawback_time": null,
            "refund_time": null,
            "remark": null,
            "seats": [
                {
                    "id": 7,
                    "order_id": 9,
                    "seat_no": "10",
                    "seat_gr": 1,
                    "seat_gc": 10,
                    "seat_sr": "1",
                    "seat_sc": "10",
                    "handle_fee": "0",
                    "sale_fee": "6000.00",
                    "pub_fee": "6000.00"
                },
                {
                    "id": 8,
                    "order_id": 9,
                    "seat_no": "11",
                    "seat_gr": 1,
                    "seat_gc": 11,
                    "seat_sr": "1",
                    "seat_sc": "11",
                    "handle_fee": "0",
                    "sale_fee": "4500.00",
                    "pub_fee": "4500.00"
                }
            ]
        }
    ]
}
```

调用示例

```java
略
```

#### 解锁
接口地址
> /plan/unlock

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>order_no</td><td>String</td><td>创建订单返回的订单编号</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": true
}
```

调用示例

```java
略
```


#### 给预约订单创建支付订单
接口地址
> /pay/genreservepay

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>必须</th><th>说明</th></tr>
	<tr><td>order_no</td><td>String</td><td>是</td><td>订单编号</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": "支付订单编号"
}
```


#### 获取当前项目影城列表
接口地址
> /store/stores

请求方式
> GET  
> POST  

输入参数

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": [
        {
            "id": 8,
            "store_no": "11",
            "store_name": "第二个11",
            "province": "北京市",
            "city": "北京市",
            "zone": "崇文区",
            "address": "12312421421421421",
            "province_no": "110000",
            "city_no": "110100",
            "zone_no": "110103",
            "config_id": 26,
            "physics_name": null,
            "systemInfo": {
                "id": 26,
                "config_name": "鼎鑫测试影院渠道"
            },
            "c_time": 1534906641000
        }
    ]
}
```

#### 获取当前项目某天的排期情况
接口地址
> /plan/projmovies

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>day</td><td>String</td><td>yyyy-MM-dd格式的日期</td></tr>
	<tr><td>sortby</td><td>String</td><td>排序规则 按时间date或者按热度hot</td></tr>
</table>

返回结果
```javascript
{
    "code":0,
    "msg":null,
    "data":[
        {
            "name":"指环王3：王者无敌",
            "img":"http://p0.meituan.net/180.250/movie/932bdfbef5be3543e6b136246aeb99b8123736.jpg",
            "score":"9.2",
            "dates":[
                {
                    "date":"2018-08-04",
                    "aliasName":"今天",
                    "plans":[
                        {
                            "cinemaId":"7",
                            "planId":"314643",
                            "startTime":"2018-08-04 12:35:00",
                            "endTime":"2018-08-04 14:35:00",
                            "subtitle":"中文",
                            "language":"英语",
                            "format":"数字",
                            "hallId":"401",
                            "hall":"奔驰IMAX厅",
                            "dimensional":"2D",
                            "size":"普通",
                            "lastUpdateTime":"2018-07-30 11:37:51",
                            "schemas":[
                                {
                                    "id":9,
                                    "item_id":8,
                                    "group_id":4,
                                    "schema_name":"2D30",
                                    "sort_num":0,
                                    "schema_status":"00",
                                    "by_permission":false,
                                    "week_rule":"1,2,3,4,5,6,7",
                                    "time_rule":"00:00,23:59",
                                    "month_rule":"-",
                                    "a_time":1532524465000,
                                    "price":30,
                                    "is_custom": true//是否为收银员自定义价格
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
}
```

#### 根据城市获取影城列表
接口地址
> /api/city_cinemas

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>city_no</td><td>String</td><td>城市编码（与城市名称传一个就可以）</td></tr>
	<tr><td>city_name</td><td>String</td><td>城市名称（与城市编码传一个就可以）</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": [
        {
            "id": 7,
            "store_basic": null,
            "store_no": "11",
            "store_name": "测试-81",
            "province": "北京市",
            "city": "北京市",
            "zone": "西城区",
            "address": "81影城地址",
            "province_no": "110000",
            "city_no": "110100",
            "zone_no": "110102",
            "config_id": 26,
            "physics_name": "测试-81",
            "systemInfo": null,
            "c_time": 1531375462000
        }
    ]
}
```

#### 根据影城id获取影片列表
接口地址
> /api/movies

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>cinema_id</td><td>String</td><td>影城id</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": [
        
        {
            "name": "心花路放",
            "img": "http://p0.meituan.net/180.250/movie/385ae00e7d42b7b016b01c1493492a02183460.jpg",
            "score": "9",
            "dates": [
                {
                    "date": "2018-09-12",
                    "aliasName": "2018-09-12",
                    "plans": [
                        {
                            "cinemaId": "7",
                            "planId": "316992",
                            "startTime": "2018-09-12 16:15:00",
                            "endTime": "2018-09-12 16:45:00",
                            "subtitle": "中文",
                            "language": "国语",
                            "format": "数字",
                            "hallId": "400",
                            "hall": "一号厅",
                            "dimensional": "2D",
                            "size": "中国巨幕",
                            "lastUpdateTime": "2018-08-31 17:34:39",
                            "planDate": "2018-09-12",
                            "standard_price": "50",
                            "current_price": "50",
                            "endClock": "16:07:00",
                            "startClock": "14:05:00"
                            "schemas": []
                        }
                    ]
                }
            ],
            "properties": {
                "id": 35,
                "film_no": "001304042014",
                "film_name": "心花路放",
                "film_subtitle": "中文",
                "film_brief": "",
                "film_content": "二手音响商耿浩（黄渤 饰）婚姻失败，他想用锤子爆小三（李晨 饰）的头，却迟迟没有勇气，幸亏在剧组做制片的兄弟郝义（徐峥 饰）及时发现自暴自弃的耿浩，他决定带着耿浩开启一段“治愈之旅”。于是一对好基友带着一只狗上路，邂逅三千公里的“桃花”。“阿凡达女郎”（陶慧 饰）、“杀马特”周丽娟（周冬雨 饰）、“白富美”（张俪 饰），各式各样的女人接连登场，耿浩一路艳遇一路疗伤。时光倒转，5年前的此时，大龄文艺女青年（袁泉 饰）因为听了一首流浪歌手耿浩的歌，毅然前往大理……在经历一连串奇葩遭遇之后，大家都放下了心里的“阴影”，找到了通向幸福的道路。",
                "film_language": "国语",
                "film_format": "数字",
                "film_dimensional": "2D",
                "film_size": "中国巨幕",
                "film_region": "多伦多电影节",
                "release_date": "2014-09-06",
                "pic_entry": "http://p0.meituan.net/180.250/movie/385ae00e7d42b7b016b01c1493492a02183460.jpg",
                "pic_small": "http://p0.meituan.net/180.250/movie/385ae00e7d42b7b016b01c1493492a02183460.jpg",
                "prevue": "http://maoyan.meituan.net/movie/videos/4668547c99504d63aa67bbcb6e5b152c.mp4",
                "film_score": "9",
                "time_diff": 118,
                "film_type": "喜剧,爱情",
                "film_alias_name": "心花路放",
                "film_source": "",
                "flag": 0,
                "a_time": 1531190239000
            }
        }
    ]
}
```

#### 影片详情
接口地址
> /api/movie_detail

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>movie_id</td><td>String</td><td>影片id（影片列表properties.id）</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "id": 34,
        "film_no": "001101232012",
        "film_name": "指环王3：王者无敌",
        "film_subtitle": "中文",
        "film_brief": "",
        "film_content": "魔戒圣战逐渐进入高潮阶段。霍比特人弗罗多（伊利亚·伍德 饰）携带着魔戒，与伙伴山姆（西恩·奥斯汀 饰）以及狡猾阴暗的咕噜（安德鲁·瑟金斯 饰）等前往末日火山，一路上艰难险阻不断。魔君索伦为阻止魔戒的销毁，用尽全力阻挠。另一方面，白袍巫师甘道夫（伊安·麦克莱恩 饰）率中土勇士们镇守刚铎首都——白城米那斯提力斯。魔兽大军压境，黑暗与光明的决战即将来临……",
        "film_language": "英语",
        "film_format": "数字",
        "film_dimensional": "2D",
        "film_size": "普通",
        "film_region": "美国",
        "release_date": "2003-12-17",
        "pic_entry": "http://p0.meituan.net/180.250/movie/932bdfbef5be3543e6b136246aeb99b8123736.jpg",
        "pic_small": "http://p0.meituan.net/180.250/movie/932bdfbef5be3543e6b136246aeb99b8123736.jpg",
        "prevue": "http://maoyan.meituan.net/movie/videos/2485dabccb39436a923caf2ad94789a9.mp4",
        "film_score": "9.2",
        "time_diff": 201,
        "film_type": "动作,冒险,奇幻",
        "film_alias_name": "指环王3：王者无敌",
        "film_source": "",
        "flag": 0,
        "a_time": 1531190238000,
        "pictures": [
            {
                "id": 206,
                "m_id": 34,
                "pic_url": "http://p0.meituan.net/180.140/movie/a0efaabe4046f4740894c376fa3d4a6c87260.jpg",
                "pic_type": "img",
                "flag": 0
            }
        ],
        "celebritys": [
            {
                "id": 270,
                "movie_id": 34,
                "cele_id": null,
                "role_type": "1000",
                "role_name": "",
                "title_img": "http://p1.meituan.net/180.250/movie/1d66d8426b28698fa2acf0e85d7708cf18379.jpg",
                "cele_name": "彼得·杰克逊",
                "cele_name_en": "Peter Jackson",
                "a_time": 1531190301000,
                "flag": 0
            }
        ]
    }
}
```

#### 获取某排期座位
接口地址
> /api/seats

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>cinema_id</td><td>Long</td><td>获取影城列表中返回的影城id</td></tr>
	<tr><td>plan_id</td><td>String</td><td>排期id</td></tr>
	<tr><td>last_update_time</td><td>String</td><td>排期最后更新时间</td></tr>
	<tr><td>hall_id</td><td>String</td><td>影厅id</td></tr>
	<tr><td>plan_date</td><td>String</td><td>排期日期 2018-03-04</td></tr>
</table>

返回结果
```javascript
{
    "code":0,
    "msg":null,
    "data":
    		"store": {
            "id": 7,
            "store_basic": null,
            "store_no": "11",
            "store_name": "测试-81",
            "province": "北京市",
            "city": "北京市",
            "zone": "西城区",
            "address": "81影城地址",
            "province_no": "110000",
            "city_no": "110100",
            "zone_no": "110102",
            "config_id": 26,
            "physics_name": null,
            "systemInfo": {
                "id": 26,
                "config_name": "鼎鑫测试影院渠道"
            },
            "c_time": 1531375462000
        },
        "plan": {
            "id": "316678",
            "movieInfo": [
                {
                    "code": "0",
                    "msg": "success",
                    "rmCode": null,
                    "rmMsg": null,
                    "cineMovieId": "48",
                    "cineMovieNum": "001101912016",
                    "movieName": "设计未来(编码测试2)",
                    "movieLanguage": "德语",
                    "movieSubtitle": "中文",
                    "movieFormat": "数字",
                    "movieDimensional": "2D",
                    "movieSize": "普通",
                    "joinStartTime": "",
                    "joinEndTime": "",
                    "success": true
                }
            ],
            "hallId": "440",
            "hallName": "百得利之星-奔驰厅",
            "startTime": "2018-09-04 12:45:00",
            "endTime": "2018-09-04 14:40:00",
            "priceType": "1",
            "price": "100",
            "marketPrice": "100",
            "lowestPrice": "30",
            "seatTotalNum": "259",
            "seatAvailableNum": "254",
            "allowBook": "1",
            "cineUpdateTime": "2018-08-31 17:33:06",
            "cinePlayId": "95606",
            "partnerPrice": "55.00",
            "priceStrategySnapshootId": null,
            "priceActivitySnapshootId": null,
            "businessDate": "2018-09-04",
            "settlePrice": null
        }，
        "seat":{
        "big_col":17,
        "big_row":13,
        "all_row":13,
        "left_seat_num":210,
        "sold_seat_num":11,
        "rows":[
            {
                "row":"1",
                "seats":[
                    {
                        "seatNo": "16590",
                        "seatPieceNo": "3",
                        "graphRow": "1",
                        "graphCol": "4",
                        "seatRow": "1",
                        "seatCol": "4",
                        "seatState": "6",//4可预订 2已锁定 0已售出 6已在本平台预订 8已在本平台售出
                        "cineSeatId": "16590",
                        "cinemaId": "11",
                        "xCoord": 1,
                        "yCoord": 4,
                        "loveseats": "",
                        "row": "1",
                        "column": "4",
                        "status": "ok",
                        "type": "danren",
                        "area_no": null,
                        "reserveOrder": {
                            "id": 5,
                            "order_no": "20180809113218025704",
                            "a_time": 1533785539000,
                            "ticket_sum": 1,
                            "plan_date": "2018-08-09",
                            "plan_time": "14:10:00",
                            "store_name": "测试-81",
                            "hall_name": "看看到底几个字大厅",
                            "film_name": "拯救大明星",
                            "total_amount": "180.00"
                        },
                        "buyOrder":null
                    },
                    {
                        "seatNo": "16587",
                        "seatPieceNo": "3",
                        "graphRow": "1",
                        "graphCol": "1",
                        "seatRow": "1",
                        "seatCol": "1",
                        "seatState": "8",
                        "cineSeatId": "16587",
                        "cinemaId": "11",
                        "xCoord": 1,
                        "yCoord": 1,
                        "loveseats": "",
                        "row": "1",
                        "column": "1",
                        "status": "ok",
                        "type": "danren",
                        "area_no": null,
                        "reserveOrder": null,
                        "buyOrder": {//如果是本平台售出的 此处会有信息
                            "id": 15,
                            "order_no": "2018080911563279910",
                            "a_time": 1533786993000,
                            "ticket_sum": 1,
                            "plan_date": "2018-08-09",
                            "plan_time": "14:10:00",
                            "store_name": "测试-81",
                            "hall_name": "看看到底几个字大厅",
                            "film_name": "拯救大明星",
                            "total_amount": "180.00"
                        },
                        "lockOrder": {//如果是本平台锁定的 此处会有信息
                            "id": 15,
                            "order_no": "2018080911563279910",
                            "a_time": 1533786993000,
                            "ticket_sum": 1,
                            "plan_date": "2018-08-09",
                            "plan_time": "14:10:00",
                            "store_name": "测试-81",
                            "hall_name": "看看到底几个字大厅",
                            "film_name": "拯救大明星",
                            "total_amount": "180.00"
                        },
                        "prelockOrder": {//如果有长时间锁定订单 此处会有信息
                            "id": 8,
                            "order_no": "20180817131642254662",
                            "a_time": 1534483003000,
                            "ticket_sum": 4,
                            "plan_date": "2018-08-17",
                            "plan_time": "14:30:00",
                            "store_name": "测试-81",
                            "hall_name": "奔驰IMAX厅",
                            "film_name": "拯救大明星",
                            "total_amount": "240.00"
                        }
                    },
                ]
            }
        ]
    }
}
```

#### 锁座创建订单
接口地址
> /api/genorder

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>cinema_id</td><td>Long</td><td>获取影城列表中返回的影城id</td></tr>
	<tr><td>plan_id</td><td>String</td><td>排期id</td></tr>
	<tr><td>plan_date</td><td>String</td><td>排期日期 格式：2018-05-05</td></tr>
	<tr><td>last_update_time</td><td>String</td><td>排期最后更新时间</td></tr>
	<tr><td>seat_ids</td><td>String</td><td>所选座位协议字符串 123,456</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "id": 25,
        "order_no": "20180810115755912215",
        "mobile": null,
        "order_status": "0000",
        "total_amount": "12000.00",
        "pay_amount": null,
        "pay_time": null,
        "check_time": null,
        "deliver_time": null,
        "ticket_sum": 1,
        "store_id": 7,
        "store_name": "测试-81",
        "hall_id": "401",
        "hall_name": "奔驰IMAX厅",
        "plan_id": "314925",
        "plan_date": "2018-08-10",
        "plan_time": "12:35:00",
        "film_name": "指环王3王者归来",
        "pic_url": null,
        "pay_type": null,
        "o_c": null,
        "handle_fee": null,
        "drawback_time": null,
        "refund_time": null,
        "remark": null,
        "seats": [
            {
                "id": null,
                "order_id": 25,
                "seat_no": "9170",
                "seat_gr": 2,
                "seat_gc": 3,
                "seat_sr": "2",
                "seat_sc": "3",
                "handle_fee": "0",
                "sale_fee": "12000.00",
                "pub_fee": "12000.00"
            }
        ]
    }
}
```

#### 获取订单详情
接口地址
> /api/order_detail

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
	<tr><td>order_no</td><td>Long</td><td>订单编号</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "id": 28,
        "order_no": "2018081015581570227",
        "mobile": null,
        "order_status": "0000",
        "total_amount": "9000.00",
        "pay_amount": null,
        "pay_time": null,
        "check_time": null,
        "deliver_time": null,
        "ticket_sum": 4,
        "store_id": 7,
        "store_name": "测试-81",
        "hall_id": "400",
        "hall_name": "一号厅",
        "plan_id": "314945",
        "plan_date": "2018-08-10",
        "plan_time": "20:35:00",
        "film_name": "指环王3王者归来",
        "pic_url": null,
        "pay_type": "0000",
        "o_c": "2000",
        "handle_fee": "0",
        "drawback_time": null,
        "refund_time": null,
        "remark": null,
        "seats": [
            {
                "id": 29,
                "order_id": 28,
                "seat_no": "3",
                "seat_gr": 1,
                "seat_gc": 3,
                "seat_sr": "1",
                "seat_sc": "3",
                "handle_fee": "0",
                "sale_fee": "6000.00",
                "pub_fee": "6000.00"
            },
            {
                "id": 30,
                "order_id": 28,
                "seat_no": "4",
                "seat_gr": 1,
                "seat_gc": 4,
                "seat_sr": "1",
                "seat_sc": "4",
                "handle_fee": "0",
                "sale_fee": "3000.00",
                "pub_fee": "3000.00"
            }
        ],
        "ticket": {
            "id": 21,
            "order_id": 28,
            "lock_flag": "388789822105",
            "ticket_flag_1": null,
            "ticket_flag_2": null,
            "ticket_refund": null
        }
    }
}
```

#### 即将上映列表 
接口地址
> /api/comming_list

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>说明</th></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": {
        "coming": [
            {
                "id": 344292,
                "haspromotionTag": false,
                "img": "http://p1.meituan.net/w.h/movie/77ecd75ac1b622d326b82a157b0cd593290526.jpg",
                "version": "v3d imax",
                "nm": "阿尔法：狼伴归途",
                "preShow": false,
                "sc": 0,
                "globalReleased": false,
                "wish": 80085,
                "star": "柯蒂·斯密特-麦菲,蕾奥娜·维埃拉,娜塔莎·迈尔兹",
                "rt": "2018-09-07",
                "showInfo": "2018-09-07 本周五上映",
                "showst": 4,
                "wishst": 0,
                "comingTitle": "9月7日 周五"
            }
        ]
    }
}
```

#### 订单列表
接口地址
> /api/orders

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>必须</th><th>说明</th></tr>
	<tr><td>pn</td><td>int</td><td>否</td><td>页码</td></tr>
	<tr><td>ps</td><td>int</td><td>否</td><td>每页条数</td></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": [
        {
            "id": 15,
            "order_no": "2018080911563279910",
            "mobile": null,
            "order_status": "2000",
            "total_amount": "18000.00",
            "pay_amount": null,
            "pay_time": null,
            "check_time": null,
            "deliver_time": null,
            "ticket_sum": 1,
            "store_id": 7,
            "store_name": "测试-81",
            "hall_id": "603",
            "hall_name": "看看到底几个字大厅",
            "plan_id": "314883",
            "plan_date": "2018-08-09",
            "plan_time": "14:10:00",
            "film_name": "拯救大明星",
            "pic_url": null,
            "pay_type": "0000",
            "o_c": "2000",
            "handle_fee": "0",
            "drawback_time": null,
            "refund_time": null,
            "remark": null,
            "seats": [
                {
                    "id": 14,
                    "order_id": 15,
                    "seat_no": "16587",
                    "seat_gr": 1,
                    "seat_gc": 1,
                    "seat_sr": "1",
                    "seat_sc": "1",
                    "handle_fee": "0",
                    "sale_fee": "18000.00",
                    "pub_fee": "18000.00"
                }
            ]
        }
    ]
}
```

#### 项目城市
接口地址
> /api/proj_citys

请求方式
> GET  
> POST  

输入参数
<table>
	<tr><th>名称</th><th>类型</th><th>必须</th><th>说明</th></tr>
</table>

返回结果
```javascript
{
    "code": 0,
    "msg": null,
    "data": [
        {
            "id": 60,
            "city_no": "110100",
            "city_name": "北京市"
        },
        {
            "id": 60,
            "city_no": "110100",
            "city_name": "北京市"
        },
        {
            "id": 2,
            "city_no": "140100",
            "city_name": "太原市"
        }
    ]
}
```

### 3、返回结果代码说明
> 0>>>成功  
> 10000>>>系统错误  
> 10001>>>请检查输入参数  
> 10002>>>影城不存在或配置错误  
> 10002>>>记录重复  
> 40001>>>影院连接失败  
> 40002>>>售票系统与影城编码不匹配  
> 40003>>>当前选择售票系统未获取到影城  
> 40004>>>座位已售出  
> 50001>>>传入的票类有误  
> 50002>>>订单编号不存在  
> 50003>>>解锁失败，请检查订单是否已经解锁，或者已经出票  
  
