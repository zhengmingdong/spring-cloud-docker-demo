# spring-cloud-docker-demo

#### 介绍
此项目为公司脚手架项目，拿来即用。spring-cloud-docker-k8s-kafka-redis-redis集群等

#### 软件架构
本项目为 基于Spring Boot开发的Spring Cloud微服务框架,版本为Greenwich.SR1
 项目目录介绍:
    service_bridge中为跨服务调用Client,用于微服务间跨服务调用.
    service_modules中为服务注册中心和网关以及过滤器等,包括服务信息观察Spring Boot Admin.
    services为单一微服务,主要服务于业务内容.
    zynn-app-common-parent父项目用于maven版本管理.
    zynn-app-common-core用于服务关联配置挂载,包括redis,kafka,elasticSearch等.
    [服务启动前请添加挂载的对应服务地址,在resources下对应的properties中]
    zynn-app-common-pojo用于统一管理 公共Bean 包括DTO,BO,VO ,枚举,通用返回Json结构内容等.
    sql主要保存业务需求对应的数据库结构及数据信息.
    [服务启动前请导入表bs_event_story_audio作为示例请求的基础表]
    服务启动包括eureka,gateway,es,user

#### 使用说明
1.  [服务启动前请添加挂载的对应服务地址,在resources下对应的properties中]

2.  [服务启动前请导入表bs_event_story_audio作为示例请求的基础表]

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
