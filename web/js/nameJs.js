/**
 * Created by ZhenZhen on 2017/6/3.
 */
$(document).ready(function () {

    $("#nameOk").click(function () {
        nameClicked();
    })
})

function nameClicked() {
    $.ajax({url:"servlet/UserServlet?nocache"+new Date().getTime(),type:"post",dataType:"json",async : false,data:{userName:$("#nameSelect").val(),nameDate:$("#nameDate").val()},success:function(data){
        drawName(data);
    },error : function(XMLHttpRequest,textStatus,errorThrown) {
        alert("failed request:"+XMLHttpRequest.readyState+"----"+XMLHttpRequest.status+"----"+textStatus+"----"+errorThrown);

    }})
}

function drawName(data) {

}