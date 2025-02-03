// assets
import { IconUsers } from '@tabler/icons-react';

// constant
const icons = {
  IconUsers
};

// ==============================|| EXTRA PAGES MENU ITEMS ||============================== //

const pages = {
  id: 'jobs',
  title: 'Jobs',
  type: 'group',
  children: [
    {
      id: 'authentication',
      title: 'Jobs',
      type: 'collapse',
      icon: icons.IconUsers,

      children: [
        {
          id: 'jobsAll',
          title: 'All Jobs',
          type: 'item',
          url: 'jobs/all-jobs',
         breadcrumbs: true
        }
      ]
    }
  ]
};

export default pages;
