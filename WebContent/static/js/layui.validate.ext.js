/*********************************
 * layui通用验证.
 * 
 * @author: ChenKj
 * @date:	2018/05/19
 ********************************/
form.verify({
	// 用户信息
	username: function(value, item) {
		if (value == "") {
			return "用户名不能为空";
		}
		
		if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			return '用户名不能有特殊字符';
	    }
	},
	realName: function(value, item) {
		if (value == "") {
			return "名称不能为空";
		}
	},
	password: function(value, item) {
		if (value == "") {
			return "密码不能为空";
		}
		
		if(value.length < 6 || value.length > 12){
			return '密码必须6位到12位';
	    }
	},
	select: function(value, item) {
		if (value == "-1") {
			return "请选择所属角色";
		}
	},
	question: function(value, item) {
		if (value == "") {
			return "找回密码问题不能为空";
		}
	},
	answer: function(value, item) {
		if (value == "") {
			return "找回密码答案不能为空";
		}
	},
	// 产品信息
	productName: function(value, item) {
		if (value == "") {
			return "产品型号不能为空";
		}
	},
	// 库存管理
	productId: function(value, item) {
		if (value == "-1") {
			return "请选择产品型号";
		}
	},
	userId: function(value, item) {
		if (value == "-1") {
			return "请选择库存所属客户";
		}
	},
	status: function(value, item) {
		if (value == "-1") {
			return "请选择产品状态";
		}
	},
	quantity: function(value, item) {
		if (value == "") {
			return "库存数量不能为空";
		}
	},
	batchNumber: function(value, item) {
		if (value == "") {
			return "批次号不能为空";
		}
	},
	contractNumber: function(value, item) {
		if (value == "") {
			return "合同号不能为空";
		}
	},
	billNumber: function(value, item) {
		if (value == "") {
			return "提单号不能为空";
		}
	},
	remark: function(value, item) {
		if (value == "") {
			return "备注不能为空";
		}
	},
	// 客户管理
	name: function(value, item) {
		if (value == "") {
			return "客户姓名不能为空";
		}
	},
	phone: function(value, item) {
		if (value == "") {
			return "客户手机不能为空";
		}
	}
});