<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<!-- 富文本编辑器引入文件 -->
<script type="text/javascript" src="/ueditor/ueditor.config.js"></script>  
<script type="text/javascript" src="/ueditor/ueditor.all.js"></script> 

<style>
body {
    background: #f0f4ff;
}
</style>
 
<body style="width:95%;height:100% ;overflow:auto">
    <div style="padding:10px 10px 20px 10px">
        <form id="actionform" method="post">
            <table cellpadding="5"  style="font-size: 14px">
                <tr>
                    <td>新闻内容:</td>
                    <td><textarea  name="news" id="news" style="width: 500px;"></textarea></td>
                </tr>
            </table>
        </form>
     </div>
</body>
 
<script>
    //ueditor编辑器
    UE.getEditor('news', {
    
           autoHeightEnabled: true,
           autoFloatEnabled: true
       });
</script>
</html>