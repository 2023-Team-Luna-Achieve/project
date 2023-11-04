import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

const CalendarComponent: React.FC = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());

  const handleDateChange = (date: Date | Date[]) => {
    if (date instanceof Date) {
      setSelectedDate(date);
      // 여기에 날짜 선택 시 실행할 코드를 추가할 수 있습니다.
    }
  };

  return (
    <div>
      <Calendar
        onChange={handleDateChange}
        value={selectedDate}
        formatDay={(locale, date) => date.getDate().toString()} // "일" 텍스트를 제거
      />
    </div>
  );
};

export default CalendarComponent;
