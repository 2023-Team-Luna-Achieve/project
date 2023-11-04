import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

const CalendarComponent: React.FC = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());

  const handleDateChange = (date: Date | Date[]) => {
    if (date instanceof Date) {
      setSelectedDate(date);
    }
  };

  return (
    <div>
      <Calendar
        onChange={handleDateChange}
        value={selectedDate}
        formatDay={(locale, date) => date.getDate().toString()} // "일" 텍스트 제거
      />
    </div>
  );
};

export default CalendarComponent;
