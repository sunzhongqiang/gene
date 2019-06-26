# gene
底层代码封装：主要对service层和dao层，以及repository层的常用和通用方法进行封装，方便进行复用，在这里我们主要认识以下几个类：

## BaseService和BaseServiceImpl 
> 主要封装类service层常用的操作接口和实现，一般我们架构中的service接口都需要继承BaseService接口，service实现都需要继承BaseServiceImpl实现，这样就不需要在重复的书写常用的save，get，find，delete接口和实现类；下面具体介绍一下基础service已经定义和实现的接口和方法；

* count() 统计数据
* delete() 删除
* get() find() 等获取单个实体的方法
* boolean	exists(ID id) 判断数据是否存在
* save() 保存和修改的方法 以及批量的保存和修改的方法
* findAll() 获取全部数据的方法

> 具体请参加下载下来生成的的javadoc

## SpringDataQueryDao和SpringDataQueryDaoImpl


