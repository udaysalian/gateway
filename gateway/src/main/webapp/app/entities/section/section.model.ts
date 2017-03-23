import { SectionLocation } from '../section-location';
export class Section {
    constructor(
        public id?: number,
        public sectionId?: number,
        public description?: string,
        public sectionType?: string,
        public sectionLocation?: SectionLocation,
    ) { }
}
