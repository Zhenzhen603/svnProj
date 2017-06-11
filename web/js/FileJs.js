
$("#check").click(function () {
    $.ajax({url:"servlet/FileServlet?nocache"+new Date().getTime(),type:"post",dataType:"text",async : false,data:{filePath:$("#filePath").val()},success:function(data){
         overview(data);
    },error : function(XMLHttpRequest,textStatus,errorThrown) {
        alert("failed request:"+XMLHttpRequest.readyState+"----"+XMLHttpRequest.status+"----"+textStatus+"----"+errorThrown);


    }})
})

$("#checkRevision").click(function () {
    $.ajax({url:"servlet/CheckRevision?nocache"+new Date().getTime(),type:"post",dataType:"json",async : false,data:{filePath:$("#filePath").val()},success:function(data){
        for(var i=0;i<data.rNs.length;i=i+1){
            $("#rN").append('<option value='+data.rNs[i]+'>'+data.rNs[i]+'</option>');
        }
        for(var i=0;i<data.rMs.length;i=i+1){
            $("#rM").append('<option value='+data.rMs[i]+'>'+data.rMs[i]+'</option>');
        }
    },error : function(XMLHttpRequest,textStatus,errorThrown) {
        alert("failed request:"+XMLHttpRequest.readyState+"----"+XMLHttpRequest.status+"----"+textStatus+"----"+errorThrown);
    }})
})

$("#diffOk").click(function () {
    $.ajax({url:"servlet/DiffServlet?nocache"+new Date().getTime(),type:"post",dataType:"text",async : false,data:{filePath:$("#filePath").val(),rN:$("#rN").val(),rM:$("#rM").val()},success:function(data){
      //  document.getElementById("diffCounts").innerHTML="My First JavaScript";
        var reg = /rM/g;
        var arr  = data.match(reg);
        document.getElementById("diffCounts").innerHTML="Diff("+arr.length+")";
        $("#fileDiffContent").val(data);
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