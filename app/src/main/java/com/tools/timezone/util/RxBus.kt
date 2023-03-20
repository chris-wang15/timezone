package com.tools.timezone.util

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

// Use object so we have a singleton instance
object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun post(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    /**
     * rxSbscription=RxBus.getInstance().toObserverable(StudentEvent.class)
    .subscribe(new Action1<StudentEvent>() {
    @Override
    public void call(StudentEvent studentEvent) {
    textView.setText("id:"+ studentEvent.getId()+"  name:"+ studentEvent.getName());
    }
    });

    @Override
    protected void onDestroy() {
    if (!rxSbscription.isUnsubscribed()){
    rxSbscription.unsubscribe();
    }
    super.onDestroy();
    }
     */
    fun <T> addToObserve(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}