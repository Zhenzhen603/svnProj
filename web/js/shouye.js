/**
 * Created by ZhenZhen on 2017/5/28.
 */
$(document).ready(function () {
    initstatusText();
    $("#updateResp").click(function () {
        alert("正在更新数据，请稍等");
        updateRespClicked();
    })
})
function updateRespClicked(){
    $.ajax({url:"servlet/Status?nocache"+new Date().getTime(),type:"post",dataType:"json",async : false,data:{resp:$("#SVNRepository").val()},success:function(data){
        //输出数据
        var dataBack="数据更新完成！</br>"+"SVN Repository:"+data.repository+"\\n"+"最新版本号："+data.revision+"最后提交日期：20"+data.commit_date+"最后提交者："+data.username+"数据更新时间:"+new Date().toLocaleDateString();
        $("#statusText").val(dataBack);
        alert("数据更新完成！");
    },error : function(XMLHttpRequest,textStatus,errorThrown) {
        alert("failed request:"+XMLHttpRequest.readyState+"----"+XMLHttpRequest.status+"----"+textStatus+"----"+errorThrown+"，请重试");

    }})
}
function initstatusText() {
    $.ajax({url:"servlet/InitStatusText?nocache"+new Date().getTime(),type:"post",dataType:"json",async : false,data:{resp:$("#SVNRepository").val()},success:function(data){
        //输出数据
        var dataBack="Welcome！\r\n-------------\r\n"+"SVN Repository: "+data.repository+"\r\n"+"最新版本号："+data.revision+"\r\n"+"最后提交日期：20"+data.commit_date+"\r\n"+"最后提交者："+data.username;
        $("#statusText").val(dataBack);

    },error : function(XMLHttpRequest,textStatus,errorThrown) {
        alert("failed request:"+XMLHttpRequest.readyState+"----"+XMLHttpRequest.status+"----"+textStatus+"----"+errorThrown+"，请刷新页面");

    }})

}