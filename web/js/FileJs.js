
$("#check").click(function () {
    $.ajax({url:"servlet/FileServlet?nocache"+new Date().getTime(),type:"post",dataType:"text",async : false,data:{filePath:$("#filePath").val()},success:function(data){
         overview(data);
    },error : function(XMLHttpRequest,textStatus,errorThrown) {
        alert("failed request:"+XMLHttpRequest.readyState+"----"+XMLHttpRequest.status+"----"+textStatus+"----"+errorThrown);


    }})
})

function overview(data) {
    if(data==-1){
        alert("请重新确认文件名！");
        $("#statusFile").val("文件查询失败，请确认文件名（文件完整路径）！\r\n"+"查询时间:"+new Date().toLocaleDateString()+" "+new Date().getHours()+":"+new Date().getMinutes());
    }else{
        $("#statusFile").val(data);
    }



}