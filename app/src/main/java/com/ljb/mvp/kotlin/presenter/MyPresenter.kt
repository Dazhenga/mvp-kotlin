package com.ljb.mvp.kotlin.presenter

import com.ljb.mvp.kotlin.contract.MyContract
import com.ljb.mvp.kotlin.protocol.dao.UsersDaoProtocol
import com.wuba.weizhang.common.LoginUser
import com.wuba.weizhang.protocol.http.UsersProtocol
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by L on 2017/7/18.
 */
class MyPresenter(private val mView: MyContract.IMyView) : MyContract.IMyPresenter {

    override fun getMvpView() = mView


    override fun startTask() {
        Observable.concat(
                UsersDaoProtocol.createObservable { UsersDaoProtocol.findUserByName(LoginUser.name) },
                UsersProtocol.getUserInfoByName(LoginUser.name))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { getMvpView().showUserInfo(it) },
                        { it.printStackTrace() }
                )
    }


    override fun logout() {
        LoginUser.name = ""
        getMvpView().logoutSuccess()
    }

    override fun onDestroy() {

    }

}