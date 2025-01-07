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
import com.opentok.android.SubscriberKit
import com.opentok.android.SubscriberKit.SubscriberListener

class ReactWebView: FrameLayout, SessionListener, PublisherListener, SubscriberListener {
  private lateinit var session: Session
  private var apiKey: String? = ""
  private var sessionId: String?= ""
  private var token: String?= ""
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

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    connectToSession()
  }

  private fun configureComponent(context: Context) {
    var params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)  
    this.setLayoutParams(params)
    // this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
  }

  fun emitOnStreamCreated(streamId: String, hasAudio: Boolean) {
    val reactContext = context as ReactContext
    val surfaceId = UIManagerHelper.getSurfaceId(reactContext)
    val eventDispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, id)
    val payload =
        Arguments.createMap().apply {
          putString("streamId", streamId)
          putBoolean("hasAudio", hasAudio)
        }
    val event = OnStreamCreatedEvent(surfaceId, id, payload)

    eventDispatcher?.dispatchEvent(event)
  }

  private fun connectToSession() {
    session = Session.Builder(context, apiKey, sessionId).build()
    session.setSessionListener(this)
    session.connect(token)
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
          session.sendSignal("onStreamReceived", "New Stream Received ${stream.streamId} in session: ${session.sessionId} hasAudio ${stream.hasAudio()}")
          emitOnStreamCreated(stream.streamId, stream.hasAudio())
          subscriber = Subscriber.Builder(context, stream).build()
          subscriber?.setStyle(
              BaseVideoRenderer.STYLE_VIDEO_SCALE,
              BaseVideoRenderer.STYLE_VIDEO_FILL
          )
          subscriber?.setSubscriberListener(this)
          // FrameLayout mubscriberViewContainer = FrameLayout(context);

          session.subscribe(subscriber)
          if (subscriber?.view != null) {
            subscriber?.view?.layoutParams = LayoutParams(1000, 1000)
            this.addView(subscriber?.view)
            session.sendSignal("addView", "subscriber?.view")
            requestLayout()
            val subscriberView = subscriber?.view
            if (subscriberView != null) {
              subscriberView.measure(
                View.MeasureSpec.makeMeasureSpec(subscriberView.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(subscriberView.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
              subscriberView.layout(subscriberView.getLeft(), subscriberView.getTop(), 640, 480)
              // subscriberView.layout(subscriberView.getLeft(), subscriberView.getTop(), subscriberView.getRight(), subscriberView.getBottom())
            }
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
            requestLayout()
  }

  override fun onStreamDestroyed(publisherKit: PublisherKit, stream: Stream) {
      session.sendSignal("onStreamDestroyed", "Publisher Stream Destroyed. Own stream ${stream.streamId}")
  }

  override fun onConnected(subscriber: SubscriberKit) {
      session.sendSignal("onConnected", "Subscriber stream ${subscriber.getStream().getStreamId()}")
  }

  override fun onDisconnected(subscriber: SubscriberKit) {
      session.sendSignal("onDisconnected", "Subscriber stream ${subscriber.getStream().getStreamId()}")
  }

  override fun onError(subscriber: SubscriberKit, opentokError: OpentokError) {
      session.sendSignal("onError", "Subscriber stream ${subscriber.getStream().getStreamId()} Error ${opentokError.message}")
  }

  override fun onError(publisherKit: PublisherKit, opentokError: OpentokError) {
      session.sendSignal("PublisherKit onError", "${opentokError.message}")
  }

  inner class OnStreamCreatedEvent(
      surfaceId: Int,
      viewId: Int,
      private val payload: WritableMap
  ) : Event<OnStreamCreatedEvent>(surfaceId, viewId) {
    override fun getEventName() = "onStreamCreated"

    override fun getEventData() = payload
  }

}