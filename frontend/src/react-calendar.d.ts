import { CalendarProps } from 'react-calendar';

declare module 'react-calendar' {
  export interface CalendarProps<ValueType = Date | Date[] | null> {
    onChange?: (value: ValueType, event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
    value?: ValueType;
    formatDay?: (_: any, date: any) => string;
  }
}
