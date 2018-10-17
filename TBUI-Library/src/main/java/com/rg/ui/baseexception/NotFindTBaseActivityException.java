package com.rg.ui.baseexception;

/**
 * Created by Administrator on 2016/7/5.
 */
public class NotFindTBaseActivityException extends  Exception{

    public NotFindTBaseActivityException (){
        this("colorful- This Activity does not extends TBaseFragmentGroupActivity !!!!!!!!");
    }
    public NotFindTBaseActivityException (String error){
        super(error);
    }
}
