/*************************************************
 * 	通用js或扩展jQuery方法
 * 	@author: Chen Kangjing
 * 	@date: 	 2018-5-13
 *************************************************/
$.fn.serializeObject = function() {  
    var o = {};  
    var a = this.serializeArray();  
    $.each(a, function() {  
        if (o[this.name]) {  
            if (!o[this.name].push) {  
                o[this.name] = [ o[this.name] ];  
            }  
            o[this.name].push(this.value || '');  
        } else {  
            o[this.name] = this.value || '';  
        }  
    });  
    return o;  
}

/** 
 * 功能：序列化form表单元素
 * 1.同名的name属性，值会被序列化为数组，例如checkbox等控件
 * 2.可以嵌套对象，name和value会被序列化为嵌套的json对象格式
 * 3.可以嵌套对象列表，name和value会被序列化成嵌套的json数组对象
 */
 $.fn.serializeNestedObject = function() {
     var json = {};  
     var arrObj = this.serializeArray();
     //alert(JSON.stringify(arrObj));
     $.each(arrObj, function() {
          // 对重复的name属性，会将对应的众多值存储成json数组
          if (json[this.name]) {  
               if (!json[this.name].push) {
                json[this.name] = [ json[this.name] ];
               }
               json[this.name].push(this.value || '');
          } else {
               // 有嵌套的属性，用'.'分隔的
               if (this.name.indexOf('.') > -1) {
                    var pos = this.name.indexOf('.');
                    var key =  this.name.substring(0, pos);
                    // 判断此key是否已存在json数据中，不存在则新建一个对象出来
                    if(!existKeyInJSON(key, json)){
                     json[key] = {};
                    }
                    var subKey = this.name.substring(pos + 1);
                    json[key][subKey] = this.value || '';
               }
               // 普通属性
               else{
                    json[this.name] = this.value || '';
               }

          }
     });

     // 处理那些值应该属于数组的元素，即带'[number]'的key-value对
     var resultJson = {};
     for(var key in json){
          // 数组元素
          if(key.indexOf('[') > -1){
               var pos = key.indexOf('[');
               var realKey =  key.substring(0, pos);
               // 判断此key是否已存在json数据中，不存在则新建一个数组出来
               if(!existKeyInJSON(realKey, resultJson)){
                resultJson[realKey] = [];
               }
               resultJson[realKey].push(json[key]);

          }
          else{ // 单元素
               resultJson[key] = json[key];
          }
     }
     return resultJson;
 };