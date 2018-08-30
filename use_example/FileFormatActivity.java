package com.obm.fa.ui;

import android.view.View;

import com.obm.hy.export.FormatActivity;
import com.obm.fa.bean.FileFormat;
import com.obm.fa.dao.DaoSession;

import java.util.List;

/**
 * Created by and on 2017-08-21.
 */

public class FileFormatActivity extends FormatActivity {


    @Override
    public void onSubmit(View view) {//还原设置
        DaoSession daoSession=BaseApplication.getInstance().getDaoSession();
        if(getIndex()==0){//导入,更新导入设置数据
            List<FileFormat> list= daoSession.getFileFormatDao().addImportData();
            daoSession.getFileFormatDao().updateInTx(list);
        }else if(getIndex()==1){//导出，更新导出设置数据
            List<FileFormat> list= daoSession.getFileFormatDao().addExportData();
            daoSession.getFileFormatDao().updateInTx(list);
        }
        super.onSubmit(view);

    }

}
