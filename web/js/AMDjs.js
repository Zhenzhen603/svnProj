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
    var myChart01 = echarts.init(document.getElementById('AMDcounts'));
    //  myChart.showLoading();
    myChart01.setOption({
        title: {
            show: false,
            text: '文件变动个数'
        },
        tooltip: {},
        legend: {
            data:['个数']
        },
        xAxis: {
            data: ["增加", "修改", "删除"]
        },
        yAxis: {},
        series: [{
            name: '个数',
            type: 'bar',
            data: data.counts

        }]
    });

    //画第二个饼图
    var myChart02 = echarts.init(document.getElementById('fileType'));
    myChart02.setOption({
        title: {
            show: false,
            text: '影响文件类型'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['java','jsp','html','xml','other']
        },
        series: [{
            name: '个数',
            type: 'pie',
            radius : '60%',
            data:[
                {value:data.typeCounts[0], name:'java'},
                {value:data.typeCounts[1], name:'jsp'},
                {value:data.typeCounts[2], name:'html'},
                {value:data.typeCounts[3], name:'xml'},
                {value:data.typeCounts[4], name:'other'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }

        }]
    });

    //画第三个饼图
    var myChart03 = echarts.init(document.getElementById('code'));
    myChart03.setOption ({
        tooltip: {
            trigger: 'axis'},
        legend: {
            data:['增加行数','删除行数'],
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: data.commit
        },
        yAxis: {
            type: 'value',
        },
        series: [
            {
                name:'增加行数',
                type:'line',
                data:data.addLine,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'删除行数',
                type:'line',
                data:data.reduceLine,
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                normal: {
                                    position: 'start',
                                    formatter: '最大值'
                                }
                            },
                            type: 'max',
                            name: '最高点'
                        }]
                    ]
                }
            }
        ]
    });

}

