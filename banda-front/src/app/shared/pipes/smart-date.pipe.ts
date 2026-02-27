import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'smartDates'
})
export class SmartDatePipe implements PipeTransform {

  transform(value: string | Date): string {
    if (!value) return '';

    const date = new Date(value);
    const now = new Date();

    const dateYear = date.getFullYear();
    const currentYear = now.getFullYear();

    const month: string = date.getMonth().toString().padStart(2,'0');
    const day: string = date.getDay().toString().padStart(2,'0');

    if (dateYear === currentYear) { // Current year, show weekday and dd/mm
      const weekday: string = new Intl.DateTimeFormat('fr-FR', {weekday: 'short'}).format(date);
      return `${capitalize(weekday)} ${day}/${month}`;
    }
    
    // Past years, show dd/mm/yy
    return `${day}/${month}/${dateYear.toString().slice(-2)}`;
  }

}

function capitalize(weekday: string): string {
  return weekday.charAt(0).toUpperCase() + weekday.slice(1);
}