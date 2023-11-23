import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainPage from './pages/MainPage';
import Header from './components/layout/Header';
import NoticePage from './pages/NoticePage';
import LoginPage from './pages/LoginPage';
import JoinPage from './pages/JoinPage';
import CommunityPage from './pages/CommunityPage';
import AboutPage from './pages/AboutPage';
import Reservationpage from './pages/ReservationPage';
import SelectPage from './pages/SelectPage';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <Header />
        <Routes>
          <Route path="" element={<MainPage />} /> {/* 루트에 대한 라우트 추가 */}
          <Route path="Main" element={<MainPage />} /> {/* 루트에 대한 라우트 추가 */}
          <Route path="About" element={<AboutPage />} />
          <Route path="Reservation" element={<Reservationpage />} />
          <Route path="Select" element={<SelectPage />} />
          <Route path="Notice" element={<NoticePage />} />
          <Route path="Login" element={<LoginPage />} />
          <Route path="Join" element={<JoinPage />} />
          <Route path="Community" element={<CommunityPage />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
