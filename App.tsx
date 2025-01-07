import React from 'react';
import {StyleSheet, View} from 'react-native';
import WebView from './specs/WebViewNativeComponent';

function App(): React.JSX.Element {
  return (
    <View style={styles.container}>
      <WebView
        apiKey="472032"
        sessionId="1_MX40NzIwMzJ-fjE3MzM0NTAzOTcyNjh-L0FQMkR0K2tVc214ajJOVzZiYWtYclg1fn5-"
        token="T1==cGFydG5lcl9pZD00NzIwMzImc2lnPTQ4YzFiYjUyOWYzM2FiYTUxZTJkYjE5NmY5ODVmN2U2ZDdlZWU3YzY6c2Vzc2lvbl9pZD0xX01YNDBOekl3TXpKLWZqRTNNek0wTlRBek9UY3lOamgtTDBGUU1rUjBLMnRWYzIxNGFqSk9WelppWVd0WWNsZzFmbjUtJmNyZWF0ZV90aW1lPTE3MzYyNzkyOTEmbm9uY2U9MC4yMTI0Nzc0NzA1NTc3Mzc5JnJvbGU9bW9kZXJhdG9yJmV4cGlyZV90aW1lPTE3Mzg4NzEyOTA1NTcmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0="
        style={styles.webview}
        onStreamCreated={(event) => {
          console.log('Stream Created ' + event.nativeEvent.streamId);
          console.log('hasAudio ' + event.nativeEvent.hasAudio);
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    alignContent: 'center',
    borderColor: 'yellow',
    borderWidth: 1,
  },
  webview: {
    width: '100%',
    height: '100%',
    backgroundColor: 'orange',
    borderColor: 'red',
    borderWidth: 1,
  },
});

export default App;