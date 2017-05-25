<%--
  Created by IntelliJ IDEA.
  User: ZhenZhen
  Date: 2017/5/21
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>servletTest</title>
    <script>
        $.get('data.json').done(function (data) {
        myChart.setOption({
            title: {
                text: '异步数据加载示例'
            },
            tooltip: {},
            legend: {
                data:['销量']
            },
            xAxis: {
                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }]
        });
    });
        // 异步加载数据
        $.get('data.json').done(function (data) {
            // 填入数据
            myChart.setOption({
                xAxis: {
                    data: data.categories
                },
                series: [{
                    // 根据名字对应到相应的系列
                    name: '销量',
                    data: data.data
                }]
            });
        });

    </script>
</head>
<body>
    <a href="servlet/Servlet01">这是一个servlet测试,以Get方式请求数据</a>>
     <!-- 注意：form需要对应一个实体类 enitity.users -->
    <form action="servlet/Servlet01" method="post">
        <input type="submit" value="post方式">
    </form>
    <h1>用户注册</h1>
    <hr>
    <form name="regForm" action="servlet/RegServlet" method="post" >
        <table border="0" width="800" cellspacing="0" cellpadding="0">
            <tr>
                <td class="lalel">用户名：</td>
                <td class="controler"><input type="text" name="username" /></td>
            </tr>
            <tr>
                <td class="label">密码：</td>
                <td class="controler"><input type="password" name="mypassword" ></td>

            </tr>
            <tr>
                <td class="label">确认密码：</td>
                <td class="controler"><input type="password" name="confirmpass" ></td>

            </tr>
            <tr>
                <td class="label">电子邮箱：</td>
                <td class="controler"><input type="text" name="email" ></td>

            </tr>
            <tr>
                <td class="label">性别：</td>
                <td class="controler">
                    <input type="radio" name="gender" checked="checked" value="Male">男
                    <input type="radio" name="gender" value="Female">女
                </td>

            </tr>


            <tr>
                <td class="label">爱好：</td>
                <td class="controler">
                    <input type="checkbox" name="favorite" value="nba"> NBA &nbsp;
                    <input type="checkbox" name="favorite" value="music"> 音乐 &nbsp;
                    <input type="checkbox" name="favorite" value="movie" title=""> 电影 &nbsp;
                    <input type="checkbox" name="favorite" value="internet"> 上网 &nbsp;
                </td>
            </tr>
            <tr>
                <td class="label">自我介绍：</td>
                <td class="controler">
                    <textarea name="introduce" rows="10" cols="40"></textarea>
                </td>
            </tr>
            <tr>
                <td class="label">接受协议：</td>
                <td class="controler">
                    <input type="checkbox" name="isAccept" value="true">是否接受霸王条款
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="注册"/>&nbsp;&nbsp;
                    <input type="reset" value="取消"/>&nbsp;&nbsp;
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
