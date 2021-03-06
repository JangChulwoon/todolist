(function (window) {
	'use strict';

	// 페이지가 전부 로드 되면 동작하도록 
		
	 var url = "/api/todos";
	 var todoCount =0;
	
	//Completed 값을 바꾸는 함수 .
   function functionUpdateComplete(id,value){
    	// value 로 change
    	var todo = new Object();
		todo.completed = value;
		var data = JSON.stringify(todo);
    	$.ajax({
            method: "put",
            url: "/api/todos/"+id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data : data
        }).done(function(data){
     	   // 성공 
        
       }).fail(function(){ 
     	  console.log("요청 실패시 호출") 
 	  })
    }
   
   function functionGetData(url){
	   $.ajax({
           method: "get",
           url: url,
           contentType: "application/json; charset=utf-8"
       }).done(function(data){
      // 가져오기 
	 	for(var i = 0; i<data.length; i++){
	 		// completed 가 1이 아니면 완료것
	 		// 다른방법 없는지 찾아볼 것.
	 		var check ="";
	 		if(data[i].completed ==1){
	 			data[i].completed = "completed";
	 			check = "checked";
	 		}else{
	 			todoCount++;
	 			data[i].completed = "";
	 		}
    		$(".todo-list").prepend(functionListForm(data[i].completed,check, data[i].id, data[i].todo));
    	 }
	 	 $('.todo-count').text(todoCount);
      });
   }
	 
	 
	 // toggle click시 값 변환 
    // on 을쓰면 새로운 이벤트도 등록가능함
	$(document).on("change",".toggle",function() {
		var id = $(this).data("id");
		if($(this).is(":checked")) {
			$(this).parents("li").addClass("completed");
			todoCount--;
			functionUpdateComplete(id,1);
			// 수정 메서드  
		}else{
			$(this).parents("li").removeClass("completed");
			todoCount++;
			functionUpdateComplete(id,0);
		}
		$('.todo-count').text(todoCount);
	});	
			
	 // delete 부분
	 $(document).on("click",".destroy",function(){
		 // 삭제하는 로직 
		 var toggle  =  $(this).siblings(".toggle");
		 var id = toggle.data("id");
		 var check = toggle.is(":checked");
		 var parent = $(this).parents("li");
			$.ajax({
	               method: "DELETE",
	               url: "/api/todos/"+id,
	               contentType: "application/json; charset=utf-8",
	               dataType: "json"
               }).done(function(data){
            	   // 만약 완료가 아니라면 카운트 지워야댐 
            	   if(!check){
            		   $('.todo-count').text(--todoCount);
            	   }
            	   parent.remove();
              }).fail(function(){ 
            	  console.log("요청 실패시 호출") 
        	  })
	 });
	 
	 // load시 데이터 가져오기
	 $(document).ready(function() { 
		 functionGetData("/api/todos");
	 });
	
	 function functionSelectInit(selec){
		 todoCount =0;
		 selec.parents("ul").children("li").children("a").removeClass("selected");
		 selec.addClass("selected");
		 $(".todo-list").empty();
	 }
	 
	 // 한번 다 지우고 불러오기
	 $("#all").on("click",function(){
		 functionSelectInit($(this));
		 functionGetData("/api/todos");
	 });

	 // 한번 다 지우고 불러오기
	 $("#completed").on("click",function(){
		 functionSelectInit($(this));
		 functionGetData("/api/todos/completed");
	 });

	 // 한번 다 지우고 불러오기
	 $("#active").on("click",function(){
		 functionSelectInit($(this));
		 functionGetData("/api/todos/uncompleted");
	 });
	 
	 //clrear
	 $("#clear").on("click",function(){
		
		 functionSelectInit($("#all"));
		 $.ajax({
             method: "DELETE",
             url: "/api/todos/completed",
             contentType: "application/json; charset=utf-8",
             dataType: "json"
         }).done(function(data){
	      	   // 만약 완료가 아니라면 카운트 지워야댐 
	        	 console.log(data);
	     }).fail(function(){ 
	      	  console.log("요청 실패시 호출") 
	  	 })
		
		 functionGetData("/api/todos");
	 });

	 // list form
    function functionListForm( completed,state,id, TodoText){
        return '<li class=' + completed+ '>'
        			+'<div class="view">'
        				+'<input class="toggle" type="checkbox"  '+ state +' data-id = "'+id+'">'
    					+'<label>' + TodoText
    					+'</label>'
    					+'<button class="destroy"></button>'
					+'</div>'
					+'<input class="edit" value="">'
				+'</li>';
    };

	$().ready(function(){

		//insert 부분 .
		 // 엔터 입력시 text, completed를 insert
		$("#input-val").keydown(function(key) {
			var text_val = $("#input-val").val();
			var completed = "0";
			var todo = new Object();

			todo.todo = text_val;
			todo.completed = completed;
			todo.date = new Date();
			var data = JSON.stringify(todo);
			
			if(text_val){
				if ( key.keyCode == 13) {
					// data 전송
					$.ajax({
		               method: "POST",
		               url: "/api/todos",
		               contentType: "application/json; charset=utf-8",
		               dataType: "json",
		               data: data
	               }).done(function(data){
	            	   // 할일 ++ 
	            	   todoCount++;
	            	   $('.todo-count').text(todoCount);  
	            	   // 입력 후 초기화
	                   $('#input-val').val('');
	                   var newlist = functionListForm("","",data.id,data.todo);
	                   $(".todo-list").prepend(newlist);
	                   // 모든 데이터에 대해서 이벤트가 등록되는 형식 .
	              });
					
				}
			}
		});
		
		
		// toggle 을 눌렀을때 동작  completed 추가/삭제 
		
		
		
	});
	
     // update 랑 delete  가져오고 , 특정 값에 대한 정보 가져올수 잇도록만 하면 끝
})(window);
