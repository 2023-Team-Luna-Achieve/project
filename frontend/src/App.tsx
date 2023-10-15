import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainPage from './pages/MainPage';
import Header from './components/layout/Header';
import NoticePage from './pages/NoticePage';
import LoginPage from './pages/LoginPage';
import CommunityPage from './pages/CommunityPage';
import AboutPage from './pages/AboutPage';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <Header />
        <Routes>
          <Route path="Main" element={<MainPage />} />
          <Route path="About" element={<AboutPage />} />
          <Route path="Notice" element={<NoticePage />} />
          <Route path="Login" element={<LoginPage />} />
          <Route path="Community" element={<CommunityPage />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
