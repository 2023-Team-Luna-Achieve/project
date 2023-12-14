import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainPage from './pages/MainPage';
import Header from './components/layout/Header';
import NoticePage from './pages/NoticePage';
import LoginPage from './pages/LoginPage';
import JoinPage from './pages/JoinPage';
import CommunityPage from './pages/CommunityPage';
import AboutPage from './pages/AboutPage';
import ReservationPage from './pages/ReservationPage';
import SelectPage from './pages/SelectPage';
import MyPage from './pages/MyPage';
import WritePage from './pages/WritePage';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <Header />
        <Routes>
          <Route path="" element={<MainPage />} />
          <Route path="Main" element={<MainPage />} />
          <Route path="About" element={<AboutPage />} />
          <Route path="Reservation" element={<ReservationPage />} />
          <Route path="Select" element={<SelectPage />} />
          <Route path="Notice" element={<NoticePage title={''} context={''} />} />
          <Route path="Login" element={<LoginPage />} />
          <Route path="Join" element={<JoinPage />} />
          <Route path="Mypage" element={<MyPage />} />
          <Route path="Community" element={<CommunityPage />} />
          <Route path="WritePage" element={<WritePage />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
