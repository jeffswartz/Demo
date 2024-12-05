import type {HostComponent, ViewProps} from 'react-native';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export interface NativeProps extends ViewProps {
    apiKey: string;
    sessionId: string;
    token: string;
  }

export default codegenNativeComponent<NativeProps>(
'OTSessionView',
) as HostComponent<NativeProps>;
