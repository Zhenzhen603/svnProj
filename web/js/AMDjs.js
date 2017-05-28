/**
 * Created by ZhenZhen on 2017/5/28.
 */



//绑定ok按钮的点击行为，并获取返回json
$(document).ready(function () {
    clicked();//页面加载完成后自动执行一次clicked
    $("#ok").click(function () {
        clicked();
    })
})
//按钮被点击所调用的方法
function  clicked() {
    $.ajax({url:"servlet/QueryAMDServlet?nocache"+new Date().getTime(),type:"post",dataType:"json",async : false,data:{date:$("#date").val()},success:function(data){
        draw(data);
    },error : function(XMLHttpRequest,textStatus,errorThrown) {
        alert("failed request:"+XMLHttpRequest.readyState+"----"+XMLHttpRequest.status+"----"+textStatus+"----"+errorThrown);

    }})
}

//设置echarts的画板
function draw(data) {
    var myChart = echarts.init(document.getElementById('AMDcounts'));
    //  myChart.showLoading();
    myChart.setOption({
        title: {
            show: false,
            text: '指定日期的修改量'
        },
        tooltip: {},
        legend: {
            data: ['文件个数']
        },
        xAxis: {
            data: ["A", "M", "D"]
        },
        yAxis: {},
        series: [{
            name: '个数',
            type: 'bar',
            data: data.counts

        }]
    });

}

