#k12soft
2018-12-14
	1、获取所有学生的收费详细（/charge/findAllStuPlan）
		由原来的按照月查询改为按学期（半年查询）
	2、创建学生收费计划函数（_createStudentCharge） line：701
	3、脚本自动扣费然后创建下一周期收费				line：226
	4、wxService.java添加微信推送计划收费函数
	
	注：
		2跟3中：添加微信服务推送，目前已经注释，等正式使用后可去掉注释