package com.webview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout;
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event
import com.opentok.android.BaseVideoRenderer
import com.opentok.android.Publisher
import com.opentok.android.PublisherKit
import com.opentok.android.PublisherKit.PublisherListener
import com.opentok.android.OpentokError
import com.opentok.android.Session
import com.opentok.android.Session.SessionListener
import com.opentok.android.Stream
import com.opentok.android.Subscriber

class ReactWebView: FrameLayout, SessionListener, PublisherListener {
  private lateinit var session: Session
  private var apiKey: String? = "472032"
  private var sessionId: String?= "1_MX40NzIwMzJ-fjE3MzM0NTAzOTcyNjh-L0FQMkR0K2tVc214ajJOVzZiYWtYclg1fn5-"
  private var token: String?= "T1==cGFydG5lcl9pZD00NzIwMzImc2lnPTNlNDZmMmY1YzQ2ZDliNzJiZWE1NzQ3MTkyNmI0MThhYTMxMjcyNjA6c2Vzc2lvbl9pZD0xX01YNDBOekl3TXpKLWZqRTNNek0wTlRBek9UY3lOamgtTDBGUU1rUjBLMnRWYzIxNGFqSk9WelppWVd0WWNsZzFmbjUtJmNyZWF0ZV90aW1lPTE3MzM0NTA0MjYmbm9uY2U9MC43Njc3ODY4NjU5MjM4NjM4JnJvbGU9bW9kZXJhdG9yJmV4cGlyZV90aW1lPTE3MzYwNDI0MjU0NDYmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0="
  private var subscriber: Subscriber? = null

  constructor(context: Context) : super(context) {
    configureComponent(context)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    configureComponent(context)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    configureComponent(context)
  }

  private fun configureComponent(context: Context) {
    connectToSession()
    var params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)  
    this.setLayoutParams(params)
  }

  private fun connectToSession() {
    session = Session.Builder(context, apiKey, sessionId).build()
    session.setSessionListener(this)
    session.connect(token)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    this.setMeasuredDimension(400, 400)
  }

  private fun publish(session: Session) {
      var publisher = Publisher.Builder(context).build()
      publisher.setPublisherListener(this)
      session.publish(publisher)
      publisher?.renderer?.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL)
      // addView(publisher.view)
  }

  public fun setApiKey(str: String?) {
    apiKey = str
  }

  public fun setSessionId(str: String?) {
    sessionId = str
  }

  public fun setToken(str: String?) {
    token = str
  }

  override fun onConnected(session: Session) {
      session.sendSignal("sessionConnected", session.getSessionId())
      // publish(session)
  }

  override fun onDisconnected(session: Session) {
      // session.sendSignal("TAG", "onDisconnected: Disconnected from session: ${session.sessionId}")
  }

  override fun onStreamReceived(session: Session, stream: Stream) {
          session.sendSignal("onStreamReceived", "New Stream Received ${stream.streamId} in session: ${session.sessionId}")
          subscriber = Subscriber.Builder(context, stream).build()
          subscriber?.renderer?.setStyle(
              BaseVideoRenderer.STYLE_VIDEO_SCALE,
              BaseVideoRenderer.STYLE_VIDEO_FILL
          )
          // this.setSubscriberListener(subscriberListener)

          session.subscribe(subscriber)
          if (subscriber?.view != null) {
            subscriber?.view?.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            this.addView(subscriber?.view)
            session.sendSignal("addView", "subscriber?.view")
            requestLayout()
          }
  }

  override fun onStreamDropped(session: Session, stream: Stream) {
      session.sendSignal("onStreamDropped", "Stream Dropped: ${stream.streamId} in session: ${session.sessionId}")
  }

  override fun onError(session: Session, opentokError: OpentokError) {
      session.sendSignal("onStreamDropped", "Session error: ${opentokError.message}")
  }

  override fun onStreamCreated(publisherKit: PublisherKit, stream: Stream) {
      session.sendSignal("onStreamCreated", "Publisher Stream Created. Own stream ${stream.streamId}")
  }

  override fun onStreamDestroyed(publisherKit: PublisherKit, stream: Stream) {
      session.sendSignal("onStreamDestroyed", "Publisher Stream Destroyed. Own stream ${stream.streamId}")
  }

  override fun onError(publisherKit: PublisherKit, opentokError: OpentokError) {
      session.sendSignal("PublisherKit onError", "${opentokError.message}")
  }
}