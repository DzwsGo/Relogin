package com.dzws.relogin.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * @author Lwang
 */
public class RxBus {
  private RxBus() {
  }

  private static volatile RxBus mRxBusInstance;

  public static RxBus get() {
    if (mRxBusInstance == null) {
      synchronized (RxBus.class) {
        if (mRxBusInstance == null) {
          mRxBusInstance = new RxBus();
        }
      }
    }
    return mRxBusInstance;
  }

  private final Subject<Object> _bus = PublishSubject.create().toSerialized();

  /**
   * 发送事件
   *
   * @param mObject
   */
  public void sendEvent(Object mObject) {
    _bus.onNext(mObject);
  }

  /**
   * 获取 被观察者
   *
   * @return
   */
  public Observable<Object> getEvent() {
    return _bus;
  }

  public <T> Observable<T> getEvent(Class<T> eventType) {
    return _bus.ofType(eventType);
  }
}
