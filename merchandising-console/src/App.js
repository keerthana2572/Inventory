import React from 'react';
import MerchandisingConsole from './components/MerchandisingConsole';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Merchandising Console</h1>
        <p>Inventory Alerts & AI Suggestions</p>
      </header>
      <main>
        <MerchandisingConsole />
      </main>
    </div>
  );
}

export default App;