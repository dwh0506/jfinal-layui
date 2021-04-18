# Jfinal-layui

#### 介绍
JFinal+layui极速开发企业应用管理系统，是以JFinal+layui为核心的企业应用项目架构，利用JFinal的特性与layui完美结合，达到快速启动项目的目的。让开发更简单高效，即使你不会layui，也能轻松掌握使用。该项目的核心功能有：登录、功能管理、角色管理（包含了权限管理）、用户管理、部门管理、系统日志、业务字典，通用的附件上传、下载、导入、导出，echart图表统计，缓存，druid的sql监控，基本满足企业应用管理系统的需求，简化了前段代码，后台公用接口都封装完善，你只需要开发业务功能即可。从后端架构到前端开发，从开发到部署，这真正的展现了jfinal极速开发的魅力。

#### 软件架构
软件架构说明：
核心架构：[jfinal](http://www.jfinal.com)，[jfinal-undertow](http://www.jfinal.com/doc/1-4)，[layui](https://www.layui.com/)，mysql，ehcach,rsa加密算法
系统权限:通过“用户-角色-功能”三者关系来实现系统的权限控制，操作简单明了，代码实现极其简单，完全可以替代shiro，你不用再去折腾shiro那一套了，这都是得益于jfinal架构的巧妙设计。
前端页面：封装了layui常用模块代码，参照使用例子，就能快速上手，无需担心不懂layui。
系统日志：操作日志、数据日志、登录日志，无需注解和手动添加，就能跟踪记录数据，不担心数据丢失


#### 友情链接

 **JFinal-layui在线演示系统：** [JFinal极速开发企业应用管理系统](http://www.qinhaisenlin.com:8080/)
 系统登录账号：admin/123456

 **JFinal-layui代码在线生成器：** [代码生成器](http://www.qinhaisenlin.com/code)

 **JFinal-layui交流群：970045838**

 **[视频教程-入门实战](https://www.qinhaisenlin.com/share/47   )

#### 安装教程

1. 新建数据库,执行doc目录下的jfinal-layui.sql
2. 修改resources下面的config-dev.txt配置文件,修改数据库配置
3. 运行com.qinhailin.common.config.ManiConfig的main方法
4. 访问地址：http://localhost;登录账号：admin/123456

#### 使用说明
jfinal的通用配置如果不是特别需要，不需要修改，直接开发你的功能即可，QQ交流群：970045838

1. controller控制类：只需继承BaseController就能拥有上传、导入、导出等通用方法。
   ControllerBind的path、viewPath默认相同，也可自定义：

```
@ControllerBind(path="/portal/core/sysUser")
public class SysUserController extends BaseController {
	@Inject
	SysUserService service;

	public void index() {
		setAttr("orgList", service.queryOrgIdAndNameRecord());
		render("index.html");
	}

	public void list() {
            //条件查询
	     Record record = new Record();
	     record.set("userName", getPara("userName"));
	     record.set("orgId", getPara("orgId"));
	     record.set("sex", getPara("sex"));
	     renderJson(service.page(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), record));
	}
 }
```

2. service服务类：只需要继承BaseService接口，实现getDao()方法，就能拥有对数据库持久层的所有方法接口。
  
```
   public class SysUserService extends BaseService {

	private SysUser dao = new SysUser().dao();
	
	@Override
	public Model<?> getDao(){
		return dao;
	}
  }

```

3. 前端页面，封装了layui常用代码，添加修改页面使用函数#@colStart和#@colEnd即可,#@colStart和#@colEnd必须成对出现
  
```
   <div class="layui-row layui-col-space1 task-row">
	#@colStart('用户编号',6)		
	   <input type="text" class="layui-input" name="sysUser.userCode" value="#(sysUser.user_code??)" 
		lay-verType='tips'lay-verify="required|" maxlength="50" placeHolder="必填"/>
	#@colEnd()
		
	#@colStart('密码',6)
	    <input type="password" class="layui-input" name="sysUser.passwd" value="#(sysUser.passwd??)"
		lay-verType='tips'lay-verify=""  maxlength="50" placeHolder="不填则使用默认密码"/>
	#@colEnd()
    </div>

```

4、分页列表，页面代码也极其简单明了

```
<script>
    //自定义弹窗
	 function userRole(obj){
		 var data=obj.data;
		var userCode=data.user_code;
		var userName=data.user_name;
		var url="#(path)/portal/core/sysUser/userRole?userCode="+userCode+"&userName="+userName;
		openDialog("配置用户角色",url,false,null,null);
	 }
	//分页表格参数
	gridArgs.title='功能';
	gridArgs.dataId='id';
	gridArgs.deleteUrl='#(path)/portal/core/sysUser/delete';
	gridArgs.updateUrl='#(path)/portal/core/sysUser/edit/';
	gridArgs.addUrl='#(path)/portal/core/sysUser/add';
	gridArgs.resetUrl='#(path)/portal/core/sysUser/resetPassword';
	gridArgs.gridDivId ='maingrid';
	initGrid({id : 'maingrid'
			,elem : '#maingrid'
			,cellMinWidth: 80
			,cols : [ [
					{title: '主键',field : 'id',width : 35,checkbox : true},						
					{title:'序号',type:'numbers',width:35},
					{title: '用户名', field: 'user_code' },
        			{title: '姓名', field: 'user_name'},
	        		{title: '所属部门', field: 'org_name'},
	        		{title: '性别', field: 'sex',templet:'#sexStr'},
        			{title: '电话', field: 'tel'},
        			{title: '手机号码', field: 'mobile'},
        			{title: '邮箱', field: 'email'},
        			{title: '允许登录', field: 'allow_login',templet:'#numToStr' },																		
					{fixed:'right',width : 180,align : 'left',toolbar : '#bar_maingrid'} // 这里的toolbar值是模板元素的选择器
			] ]
			,url:"#(path)/portal/core/sysUser/list"
			,searchForm : 'searchForm'
		},{role:userRole});
	
</script>

<script type="text/html" id="sexStr">
    {{ d.sex == 1 ? '男' : '女' }}             
</script>
<script type="text/html" id="numToStr">
    <input type="checkbox" name="isStop" {{(d.id=='admin'||d.id=='superadmin')?'disabled':''}} value="{{d.id}}" 
		lay-skin="switch" lay-text="是|否" lay-filter="allowLoginFilter" {{ d.allow_login == 0 ? 'checked' : '' }}>               
</script>
```
5、业务字典快速引用函数
**#@getSelect(code,name,text)** ； **#@getRadio(code,name,text)** ；**#@getCheckbox(code,name,text)** 
code:字典编号，name:元素name属性,text:选项名称，需要选中值，在调用之前设置值即可：#set(value='选中值')，如：系统日志类型引用：
```
   #@queryStart('日志类型')					
	#@getSelect('logType','remark','日志类型')			
   #@queryEnd() 
   #@queryStart('日志类型')
        #set(value='数据日志')					
	#@getRadio('logType','remark','日志类型')			
   #@queryEnd() 
   #@queryStart('日志类型')
        #set(value='操作日志,数据日志,登录日志')					
	#@getCheckbox('logType','remark','日志类型')			
   #@queryEnd() 
```
![业务字典快速引用](https://images.gitee.com/uploads/images/2019/0107/190356_a4e5ac71_1692092.png "日志类型快速引用实例.png")


#### 系统界面
1、登录界面，第一次不显示验证码，输错一次密码，则需要验证码
![第一次登录界面](https://images.gitee.com/uploads/images/2019/0105/215040_a8a2fc5f_1692092.png "登录登录.png")
![密码错误，显示验证码](https://images.gitee.com/uploads/images/2019/0105/215235_6a995c90_1692092.png "显示验证码.png")
2、登录后的管理主页
![管理主页](https://images.gitee.com/uploads/images/2019/0105/215505_6151b7da_1692092.png "管理主页.png")
3、系统管理核心模块
![功能管理](https://images.gitee.com/uploads/images/2019/0105/215623_059ce33f_1692092.png "功能管理.png")
![角色管理](https://images.gitee.com/uploads/images/2019/0105/215705_08c4c892_1692092.png "角色管理.png")
![用户管理](https://images.gitee.com/uploads/images/2019/0105/215739_245dccdd_1692092.png "用户管理.png")
![部门管理](https://images.gitee.com/uploads/images/2019/0301/085710_77180688_1692092.jpeg "部门管理.jpg")
![业务字典](https://images.gitee.com/uploads/images/2019/0105/215832_91d9f78c_1692092.png "业务字典.png")
![系统日志](https://images.gitee.com/uploads/images/2019/0105/215909_00d4c9e0_1692092.png "系统日志.png")
![附件上传](https://images.gitee.com/uploads/images/2019/0105/220039_83ff97e3_1692092.png "附件上传.png")
![附件下载](https://images.gitee.com/uploads/images/2019/0105/220152_c1c0a0fc_1692092.png "附件下载.png")
![echart图表](https://images.gitee.com/uploads/images/2019/0105/220239_fea15866_1692092.png "echart图表.png")

 **4、响应式布局展示：** 

![移动端主菜单](https://images.gitee.com/uploads/images/2020/0121/175529_c0d9ea9e_1692092.png "移动端菜单.png")
![功能管理菜单树](https://images.gitee.com/uploads/images/2020/0121/175635_e8d6b7c6_1692092.png "功能管理菜单树.png")

![功能管理列表](https://images.gitee.com/uploads/images/2020/0121/175750_8fe89fde_1692092.png "功能管理列表.png")
![弹窗展示](https://images.gitee.com/uploads/images/2020/0121/175932_f51966c2_1692092.png "弹窗展示.png")

![用户管理列表](https://images.gitee.com/uploads/images/2020/0121/180430_16c9fab1_1692092.png "用户管理列表.png")
![删除提示](https://images.gitee.com/uploads/images/2020/0121/180754_f657eab0_1692092.png "删除提示.png")

感兴趣的攻城狮可以参考，希望能对你有帮助。