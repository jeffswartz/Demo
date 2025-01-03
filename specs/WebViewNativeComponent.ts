import type {HostComponent, ViewProps} from 'react-native';
// import type {BubblingEventHandler} from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

type WebViewStreamCreatedEvent = {
  streamId: string;
};

export interface NativeProps extends ViewProps {
    apiKey: string;
    sessionId: string;
    token: string;
    onStreamCreated?: BubblingEventHandler<WebViewStreamCreatedEvent> | null;
    // subscribeToStream(streamId: string): Promise<string>;
}

export default codegenNativeComponent<NativeProps>(
  'CustomWebView',
) as HostComponent<NativeProps>;