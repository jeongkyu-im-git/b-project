<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>book-project</title>
    <style>
    /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }
    
        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 5px;
            border: 1px solid #888;
            width: 900px; /* Could be more or less, depending on screen size */                          
        }
    </style>
    <script src="/js/jquery/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <script>
    $(document).ready(function () {
    	
    	var loginChk = "<%=((String)session.getAttribute("LOGIN_USER_NAME")==null)?false:true %>";
    	if ( loginChk == "false" ) {
    		 $("#step1").show();
             $("#divSearch").hide();
    	} else {
    		$("#step1").hide();
            $("#divSearch").show();
            viewTab(1);
    	}
    	
    	// registration
    	//$("#registForm").submit( function (event) {
    		
        //    event.preventDefault();
    		
         //   registration_submit();
    	//});
    	$("#registForm").validate({
    	     submitHandler: function(form) {
    	       //$(form).ajaxSubmit();
    	    	 registration_submit();
    	     }
    	})  
    	
    	// login
    	//$("#loginForm").submit( function (event) {    		
        //    event.preventDefault();
        //    login_submit();
    	//});
    	
    	$("#loginForm").validate({
    		submitHandler: function(form) {
    			login_submit();
    		}
    	}) 
    	
    	// search
    	$("#searchForm").submit( function (event) {
    		
            event.preventDefault();

            $("#page").val("1");
            search_submit();
    	});
    	
    	$(function() { $("#registForm").validate(); });
    	$(function() { $("#loginForm").validate(); });
    });
    
    //회원가입 Ajax
    function registration_submit() {

    	var member = {}
        member["username"] = $("#username").val();
        member["email"] = $("#email").val();
        member["password"] = $("#password1").val();

        $("#btn-regist").prop("disabled", true);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/member/registration",
            data: JSON.stringify(member),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {

                //var json = "<h4>Ajax Response</h4><pre>"
                //    + JSON.stringify(data, null, 4) + "</pre>";
                //$('#feedback_regist').html(json);
                alert(data.msg);

                console.log("SUCCESS : ", data);
                $("#btn-regist").prop("disabled", false);
                
                location.href = "/";

            },
            error: function (e) {

                //var json = "<h4>Ajax Response</h4><pre>"
                //    + e.responseText + "</pre>";
                //$('#feedback_regist').html(json);
                alert(e.responseJSON.msg);

                console.log("ERROR : ", e);
                $("#btn-regist").prop("disabled", false);

            }
        });

    }
    
    //로그인 Ajax
    function login_submit() {
    	
        var member = {}
        member["email"] = $("#l_email").val();
        member["password"] = $("#l_password1").val();

        $("#btn-login").prop("disabled", true);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/member/login",
            data: JSON.stringify(member),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {

                //var json = "<h4>Ajax Response</h4><pre>"
                //    + JSON.stringify(data, null, 4) + "</pre>";
                //$('#feedback_login').html(json);
                
                alert(data.msg);

                console.log("SUCCESS : ", data);
                $("#btn-login").prop("disabled", false);
                
                location.href = "/";

            },
            error: function (e) {

                //var json = "<h4>Ajax Response</h4><pre>"
                //    + e.responseText + "</pre>";
                //$('#feedback_login').html(json);
                alert(e.responseJSON.msg);

                console.log("ERROR : ", e);
                $("#btn-login").prop("disabled", false);
                
                $("#step1").show();

            }
        });

    }
    
    //로그인 Ajax
    function logout() {

        var member = {};
        
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/member/logout",
            data: JSON.stringify(member),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {

                alert(data.msg);

                console.log("SUCCESS : ", data);

                location.href = "/";
            },
            error: function (e) {

                //var json = "<h4>Ajax Response</h4><pre>"
                //    + e.responseText + "</pre>";
                //$('#feedback_login').html(json);
                alert(e.responseJSON.msg);

                console.log("ERROR : ", e);
                
                
                $("#step1").hide();

            }
        });

    }
    
    //책검색  Ajax
    var documents;
    function search_submit() {
    	var total_count = 0;
    	
        var search = {}
        search["query"] = $("#query").val();
        search["page"] = $("#page").val();
        search["size"] = $("#size").val();

        $("#btn-search").prop("disabled", true);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/book/search",
            data: JSON.stringify(search),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	
            	$("#search_result > tbody").html("");
                
                var meta = data.meta;
                total_count = meta.total_count;
                if ( parseInt(total_count) == 0 ) {
                	$('#search_result > tbody:last')
                	.append('<tr>')
                	.append('<td colspan="5"> 검색결과가 존재하지 않습니다.</td>') // 썸네일
                	.append('</tr>');
                } else {
                	documents = data.documents;
                    $.each(documents, function(i, item) {
                    	$('#search_result > tbody:last')
                    	.append('<tr>')
                    	.append('<td><img src="'+item.thumbnail+'"/></td>') // 썸네일
                    	.append('<td><a href="javascript:goDetail('+i+')">'+item.title+'</a></td>') // 제목
                    	.append('<td>'+item.authors[0]+'</td>') // 저자
                    	.append('<td>'+item.publisher+'</td>') // 출판사
                    	.append('<td>'+item.sale_price+'원</td>') // 판매가격
                    	.append('</tr>');
                    	
                    });
                       	
                }
                
             // 페이징 처리
                var getPaging = pagingObject($("#page").val(), total_count, $("#size").val()); 
                $('#paging').html(getPaging);
                
                console.log("SUCCESS : ", data);
                $("#btn-search").prop("disabled", false);
                
            },
            error: function (e) {

            	alert(e.responseJSON.msg);
                //var json = "<h4>Ajax Response</h4><pre>"
                //    + e.responseText + "</pre>";
                //$('#book-list').html(json);
                
                console.log("ERROR : ", e);
                $("#btn-search").prop("disabled", false);

            }
        });
        
    }
    var modal = document.getElementById("myModal");
    function goDetail(index) {
    	//alert(documents[index]);
    	$('#book-detail').html("");
    	var detail = '<table width="100%" border="1"><tr><td width="120px"><img src="'+documents[index].thumbnail+'"/></td>';
    	detail += '<td width="*"><span>';
    	detail += 'o 제목 : ' + documents[index].title + '<br/>';
    	detail += 'o ISBN : ' + documents[index].isbn + '<br/>';
    	if ( documents[index].authors.length == 1 ) {
    		detail += 'o 저자 : ' + documents[index].authors + '<br/>';	
    	} else {
    		detail += 'o 저자 : ' + documents[index].authors + '외 '+(documents[index].authors.length-1)+'명<br/>';
    	}
    	detail += 'o 출판사 : ' + documents[index].publisher + '<br/>';
    	detail += 'o 출판일 : ' + documents[index].datetime + '<br/>';
    	detail += 'o 정가 : ' + documents[index].price + '원<br/>';
    	detail += 'o 판매가 : ' + documents[index].sale_price + '원<br/><br/>';
    	detail += 'o 소개 : ' + documents[index].contents + '<br/>';
    //	detail += '[<a href="javascript:closeDetail();">닫기</a>]';
    	detail += '</span></td></tr></table>';
    	$('#book-detail').html(detail);
    	$('#myModal').show();
    }
    
    // 나의 검색 이력
    function viewMyHistory() {
    	
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/book/myhistory",
            data: JSON.stringify({}),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	
            	//$('#myHistory').html(data);
            	var json = "<h4>나의 검색 이력</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#myHistory').html(json);
                
            },
            error: function (e) {

            	alert(e.responseJSON.msg);
                //var json = "<h4>Ajax Response</h4><pre>"
                //    + e.responseText + "</pre>";
                //$('#book-list').html(json);
                
                console.log("ERROR : ", e);

            }
        });
        
    }
    
    // 인기키워드 리스트
	function viewHitHistory() {
    	
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/book/hithistory",
            data: JSON.stringify({}),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	
            	var json = "<h4>인기 검색어</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#hit-list').html(json);
                
            },
            error: function (e) {

            	alert(e.responseJSON.msg);
                
            }
        });
        
    }
    </script>
</head>
<body>
    <h2>Hello, Book Project</h2> 
    
    <table border="1" id="step1" width="900px">
    <tr>
    	<td align="center" width="150px;"><b>회원가입</b></td>
    	<td align="left" width="750px;">
    	<div id="divMember">
    <form id="registForm">
    <fieldset>
    	<p>
    		<label for="cname">이름 (필수)</label> : <input type="text" id="username" name="username" value="" class="required" minlength="2" />
    	</p>
    	<p>
    		<label for="cemail">이메일 (필수)</label> : <input type="text" id="email" name="email" value="" class="required email"/>
    	</p>
    	<p>
    		<label for="cemail">패스워드 (필수)</label> : <input type="password" id="password1" name="password1" value="" class="required" minlength="8"/>
    	</p>
    	<input type="submit" id="btn-regist" name="btn-regist" value="가입 신청"></input></br>
    	<div style="display:none">처리결과 : <span id="feedback_regist" ></span></div>
    </fieldset>
    </form> 
    </div>
    	</td>
    </tr>
    <tr>
    	<td align="center" ><b>로그인</b></td>
    	<td align="left" width="750px;">
    	<div id="divLogin">
     <form id="loginForm">
     <fieldset>
     	<p>
    		<label for="cemail">이메일 (필수)</label> : <input type="text" id="l_email" name="l_email" value="" class="required email"/>
    	</p>
    	<p>
    		<label for="cemail">패스워드 (필수)</label> : <input type="password" id="l_password1" name="l_password1" value="" class="required" minlength="8"/>
    	</p>
    <input type="submit" id="btn-login" name="btn-login" value="LOGIN"></input></br>
    <div style="display:none">처리결과 : <span id="feedback_login"></span></div>
    sample account : jeongkyu.im@gmail.com / 12345678
    </fieldset>
     </form>
     
    </div>
    	</td>
    </tr>
 
    </table>
       
    
    <div id="divSearch">
    <h2>책검색 /상세조회 /검색히스토리/인기키워드목록(최대10개)</h2>
    <h3>로그인 사용자 : <%=(String)session.getAttribute("LOGIN_USER_NAME") %> [<a href="javascript:logout();"/>로그아웃</a>]</h3>
    [ <a href="javascript:viewTab(1)">책검색</a> | <a href="javascript:viewTab(2);">나의 검색히스토리 | <a href="javascript:viewTab(3);">인기키워드</a> ]<br/>
    <div id="myHistory">myHistory</div>
    <div id="hit-list">hit-list</div>
    <div id="book-list">
    <form id="searchForm">
    검색키워드 : <input type="text" id="query" value="홍길동" />
         <input type="hidden" id="page" value="1"/>
         <input type="hidden" id="size" value="5"/> 
        <input type="submit" id="btn-search" name="btn-search" value="검색"/><br/>
       	<table id="search_result" border="1px" width="900px">
       	<thead>
       	<tr>
       		<th width="120px;">썸네일</th>
       		<th width="*">제목</th>
       		<th width="100px;">저자</th>
       		<th width="100px;">출판사</th>
       		<th width="100px;">판매가격</th>
       	</tr>
       	</thead>
       	<tbody>
       	    <td colspan="5"> 책의 제목을 입력하세요. </td>
       	</tbody>
       	</table>
       <div id="paging"></div>
       <div id="myModal" class="modal">
 
      <!-- Modal content -->
      <div class="modal-content">
      	<p style="text-align: center;"><span style="font-size: 14pt;"><b><span style="font-size: 20pt;">상세내용</span></b></span></p>
      	<div id="book-detail" style="padding:5px;"></div>
        <div style="cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;" onClick="close_pop();">
        <span class="pop_bt" style="font-size: 13pt;" >닫기</span>
        </div>
      </div>
 
    </div>
       </div>
 
    <script>
    function viewTab(idx) {
    	if (idx == 1) {
    		$('#book-list').show();
    		$('#myHistory').hide();
    		$('#hit-list').hide();
    	} else if ( idx == 2 ) {
    		$('#book-list').hide();
    		$('#myHistory').show();
    		$('#hit-list').hide();
    		
    		viewMyHistory();
    	} else if ( idx == 3 ) {
    		$('#book-list').hide();
    		$('#myHistory').hide();
    		$('#hit-list').show();
    		
    		viewHitHistory();
    	}
    }
    //팝업 Close 기능
    function close_pop(flag) {
    	$('#myModal').hide();
    };

    function goPaging( pageIdx ) {
    	
    	$("#page").val(pageIdx);
    	search_submit();
    }
    
    function pagingObject(startIndex, totalCount, pageBlock) {

        var pagingHTML 		= "";
        var page            = parseInt(startIndex);     // 현재 페이지 번호
        var totalCount      = parseInt(totalCount);     // 전체 로우 카운트  
        var pageBlock		= parseInt(pageBlock);      // 한 페이지에 표시될 Row 카운트
        var navigatorNum    = 10;                       // 페이지 네비게이션 카운트
        var firstPageNum	= 1;

        var lastPageNum		= Math.floor((totalCount-1)/pageBlock) + 1;
        var previewPageNum  = page == 1 ? 1 : page-1;
        var nextPageNum		= page == lastPageNum ? lastPageNum : page+1;
        var indexNum		= startIndex <= navigatorNum  ? 0 : parseInt((startIndex-1)/navigatorNum) * navigatorNum;

        if (totalCount > 1) {
            if (startIndex >= 1) {
                pagingHTML += "<a class='btn_first disabled' href='javascript:goPaging("+firstPageNum+");' id='"+firstPageNum+"'><em><<</em></a> ";
                pagingHTML += "<a class='btn_prev disabled' href='javascript:goPaging("+previewPageNum+");' id='"+previewPageNum+"'><em><</em></a> ";
            }

            for (var i=1; i<=navigatorNum; i++) {
                var pageNum = i + indexNum;

                // 현재 선택값
                if (pageNum == startIndex)
                    pagingHTML += "<b><a class='selected' href='javascript:goPaging("+pageNum+");' id='"+pageNum+"'>"+pageNum+"</a></b> ";

                else
                    pagingHTML += "<a href='javascript:goPaging("+pageNum+");' id='"+pageNum+"'>"+pageNum+"</a>  ";

                if (pageNum==lastPageNum || pageNum == 100 )
                    break;
            }

            if (startIndex < lastPageNum && pageNum != 100) {
            	if ( nextPageNum < 100) {
            		pagingHTML += "<a class='btn_next' href='javascript:goPaging("+nextPageNum+");' id='"+nextPageNum+"'><em>></em></a> ";	
            	} else {
            		pagingHTML += "<a class='btn_next' href='javascript:goPaging("+100+");' id='100'><em>></em></a> ";
            	}
            	if ( lastPageNum < 100) {
            		pagingHTML += "<a class='btn_end' href='javascript:goPaging("+lastPageNum+");' id='"+lastPageNum+"'><em>>></em></a>";	
            	} else {
            		pagingHTML += "<a class='btn_end' href='javascript:goPaging(100);' id='100'><em>>></em></a>";
            	}
                
            }
        }

        return pagingHTML;
    }
    </script>
    
    </form>
    </div>
</body>
</html>
