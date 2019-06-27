# gene
底层代码封装：主要对service层和dao层，以及repository层的常用和通用方法进行封装，方便进行复用，在这里我们主要认识以下几个类：BaseService和BaseServiceImpl ,SpringDataQueryDao和SpringDataQueryDaoImpl

## 用法

#### gradle
```groovy
maven { url "http://101.200.215.10:8083/repository/maven-public/" }

dependencies {
    implementation 'com.mmk:gene:2.1.2.RELEASE'
}
```

#### maven
```xml
<repository>
  <id>lcdt</id>
  <name>lcdt repo</name>
  <url>http://101.200.215.10:8083/repository/maven-public/</url>
  <releases>
    <enabled>true</enabled>
  </releases>
  <snapshots>
    <enabled>false</enabled>
  </snapshots>
</repository>
        
<dependency>
    <groupId>com.mmk</groupId>
    <artifactId>gene</artifactId>
    <version>2.1.2.RELEASE</version>
</dependency>
```

## BaseService和BaseServiceImpl 
> 主要封装类service层常用的操作接口和实现，一般我们架构中的service接口都需要继承BaseService接口，service实现都需要继承BaseServiceImpl实现，这样就不需要在重复的书写常用的save，get，find，delete接口和实现类；下面具体介绍一下基础service已经定义和实现的接口和方法；

#### 基础的方法
* count() 统计数据
* delete() 删除
* get() find() 等获取单个实体的方法
* boolean	exists(ID id) 判断数据是否存在
* save() 保存和修改的方法 以及批量的保存和修改的方法
* findAll() 获取全部数据的方法

> 具体请参加下载下来生成的的javadoc

#### 用法
> 所有的 service 接口继承 BaseService 接口 所有service 实现继承BaseServiceImpl 这样既获得了以上的方法并可以再项目中使用，不需要重新书写上面的基础方法了；

## SpringDataQueryDao和SpringDataQueryDaoImpl
> 主要封装数据访问和持久层的基础和通用的方法方便使用和快速开发
#### 基础方法
来自JpaRespository的方法：
* save()
* delete()
* findBy*()
* get()


来自SpringDataQueryDao的方法
* query*() 各种查询方法
* count*() 各种统计总和的方法
* find*() get*() 各种获取单个实体的方法
* execute*() 各种update或者delete方法
以上方法都分别都有支持sql和jpql的方法
#### 用法

> 项目中所有的repository 接口都需要继承JpaRepository 这样就获得了基本的CRUD和分页能力，所有的dao接口都需要继承SpringDataQueryDao 所有的dao实现都继承SpringDataQueryDaoImpl类，这样就获得了基本的数据访问能力


