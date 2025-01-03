import React from 'react';
import {Alert, StyleSheet, View, Text} from 'react-native';
import WebView from './specs/WebViewNativeComponent';

function App(): React.JSX.Element {
  return (
    <View style={styles.container}>
      <Text>foo</Text>
      <WebView
        apiKey="472032"
        sessionId="1_MX40NzIwMzJ-fjE3MzM0NTAzOTcyNjh-L0FQMkR0K2tVc214ajJOVzZiYWtYclg1fn5-"
        token="T1==cGFydG5lcl9pZD00NzIwMzImc2lnPTNlNDZmMmY1YzQ2ZDliNzJiZWE1NzQ3MTkyNmI0MThhYTMxMjcyNjA6c2Vzc2lvbl9pZD0xX01YNDBOekl3TXpKLWZqRTNNek0wTlRBek9UY3lOamgtTDBGUU1rUjBLMnRWYzIxNGFqSk9WelppWVd0WWNsZzFmbjUtJmNyZWF0ZV90aW1lPTE3MzM0NTA0MjYmbm9uY2U9MC43Njc3ODY4NjU5MjM4NjM4JnJvbGU9bW9kZXJhdG9yJmV4cGlyZV90aW1lPTE3MzYwNDI0MjU0NDYmaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0="
        style={styles.webview}
        onStreamCreated={(event: Any) => {
          console.log('Stream Created ' + Object.keys(event) + event.type);
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