# export
# 开发场景
1.用户需要导入文件（导入的文件有多种格式：xls,txt,csv）到手机中，存到sqlite数据库中，然后通过手机操作查询，修改数据等操作后，用户需要将数据导出，生成新的文件（导出文件有多种格式：xls,txt,csv）.

2.每个客户要导入时的条件可能都是不一样的，导出时的要求也是不一样的。  
   例如：  
   导入文件格式不同（xls,txt，csv）  
   导入文件是不是有包含标题行  
   如果导入文件是txt格式,那么每一列数据是通过是什么符号来区分开（即分隔符）  
   标题名可能是不一样的  
   导入导出的字段排列顺序（按排序序号将数据写入对应的数据库表字段）  
   导入导出时忽略某列数据（即某列数据不导入）  
   导入或导出时的文件名称不一样，文件的存放位置要求也不一样。 
   再次导入时是否覆盖原有的数据，导出时是否删除数据。  

# 实现
根据以上需求，写了export这个module,也编译生成了export.arr文件（实现了以前所提及的）。可以通过界面设置来决定如何导入导出文件。  
	因为涉及到界面，或许会因为你自己的工程一些资源id相同或者主题Theme不同而产生问题，不过没关系，不要太懒，直接在我提供的源代码中修改，重新编译生产新的arr就好。
	希望能给需要的人提供一些思路。
>主要实现技术:java 反射等  

# 使用
### 1.項目引入export.arr这个包
将export.arr复制到libs文件夹下
修改bulid.gradle(app)文件：
`compile(name: 'export', ext: 'aar')`

### 2.新增两个类：FileFormat.java,FileFormatDao.java(见附件示例代码(use_example文件下))
FileFormat.java(文件格式类)基本不用作修改，直接使用。
FileFormatDao.java(文件格式操作类)主要实现以下两个方	法(具体实现可参考附件示例代码)：
```
public List<FileFormat> addExportData()

public List<FileFormat> addImportData()
```
### 3.在应用第一次进入时(在Application中)，应进行导入导出设置数据的初始化
```
//判断是否是第一次进入
if (getSpUtil().getBoolean(Constants.FISRT_ENTER, true)) {
    //初始化导出设置
    List<FileFormat> list = daoSession.getFileFormatDao().addExportData();
    //初始化导入设置
    list.addAll(daoSession.getFileFormatDao().addImportData());
    daoSession.getFileFormatDao().deleteAll();
    daoSession.getFileFormatDao().insertInTx(list);
    getSpUtil().addBoolean(Constants.FISRT_ENTER, false);
}
```
### 4.新建一个Activity,用于显示文件格式设置界面：
具体代码可参考示例代码中的FileFormatActivity.java
**注意：FileFormatActivity必须继承FormatActivity**

### 5.导入设置,选择文件类型：
![图片.png](https://upload-images.jianshu.io/upload_images/1934363-33e4e8016a13900e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
代码示例:
在res/vaules/arrays.xml文件中：
```
<string-array name="file_type_in">
<item>操作员</item>
<item>部门表</item>
<item>员工表</item>
<item>位置表</item>
<item>固定资产表</item>
</string-array>

<string-array name="file_type_value_in">
<item>USER</item>
<item>DEPARTMENT</item>
<item>EMPLOYEE</item>
<item>LOCATION</item>
<item>ASSET</item>
</string-array>
```
>注意：以上的USER、DEPARTMENT等对应的是表名(决定要将数据导入哪个表)

### 6.导出设置,选择文件类型
![图片.png](https://upload-images.jianshu.io/upload_images/1934363-62bda351e93dc857.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
代码示例:
```
<string-array name="file_type_out">
<item>导出数据</item>
</string-array>

<string-array name="file_type_value_out">
<item>EXPORT</item>
</string-array>
```
### 7.界面颜色设置
![图片.png](https://upload-images.jianshu.io/upload_images/1934363-dfb0d143fb2be32e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
1.区域的颜色设置：
<color name="top_bar_color">#dd5862</color>

2.区域的颜色设置：
<color name="indicator_color">#39A1E8</color>

3.图标的替换：
在/res/mipmap/中放置图标，命名为export_right_tw.png
建议大小40*40.
```
### 8.导入导出方法使用
**导入：**
```
/**
 * 导入文件
 * @param dbname    数据库名称
/**
 * 导入文件
 * @param dbname    数据库名称
 * @param version  数据库版本
 * @param tableName 文件格式设置表
 * @param fileTypeName 文件类型名称（表名+In）
 * @param fileType 文件类型编码（表名）
 * @param filepath 文件完整路徑
 * @param filename     文件名称
 * @param delete       是否删除(覆盖)之前数据
 * @param mHandler     Handler
 * @return 是否成功
 */


public void ImportFile(Context context, String dbname, int version, String tableName, 
                      String fileTypeName, String fileType,String filepath,
                      boolean delete,String filename, Handler mHandler)

formartUtil.ImportFile(getActivity(), "fa.db", DaoMaster.SCHEMA_VERSION,         
                     FileFormatDao.TABLENAME, UserIn,USER,
                    ”storage/sdcard0//固定资产/操作员”, fileName,true, mHandler);
```
**导出：**
```
/**
 *
 * @param context 上下文
 * @param dbname    数据库名称
 * @param version   数据库版本
 * @param tableName 文件格式设置表
 * @param fileType  导入或导出
 * @param path  导出路径
 * @param list1 导出的数据
 * @param <E>
 * @return
 * @throws java.io.IOException
 */
OutputFile(Context context,String dbname,int version,
            String tableName ,String fileType,String path,List<E> list1)

exportHelper.OutputFile(getActivity(), "fa.db", DaoMaster.SCHEMA_VERSION,
               FileFormatDao.TABLENAME, "EXPORT", finalPath, exportList)

```



