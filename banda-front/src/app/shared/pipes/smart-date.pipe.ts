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

    if (dateYear < currentYear) {
      return `${date.getMonth()}/${dateYear}`
    }


    return `${date.getMonth()}`;
  }

}
