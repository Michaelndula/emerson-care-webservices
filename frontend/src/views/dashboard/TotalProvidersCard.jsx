import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

// material-ui
import { useTheme } from '@mui/material/styles';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';

// project imports
import MainCard from 'ui-component/cards/MainCard';
import SkeletonEarningCard from 'ui-component/cards/Skeleton/EarningCard';

// assets
import EarningIcon from 'assets/images/icons/earning.svg';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import GetAppTwoToneIcon from '@mui/icons-material/GetAppOutlined';
import FileCopyTwoToneIcon from '@mui/icons-material/FileCopyOutlined';
import PictureAsPdfTwoToneIcon from '@mui/icons-material/PictureAsPdfOutlined';
import ArchiveTwoToneIcon from '@mui/icons-material/ArchiveOutlined';
import GroupIcon from '@mui/icons-material/Group';

const TotalProvidersCard = ({ isLoading, total, icon, label }) => {
  const theme = useTheme();
  const [totalProviders, setTotalProviders] = useState(0);

     useEffect(() => {
        const token = localStorage.getItem('token');
        axios
          .get('/api/providers/all' , { headers: { Authorization: `Bearer ${token}` } })
          .then((response) => {
            if (Array.isArray(response.data)) {
              setTotalProviders(response.data.length);
            } else {
              setTotalProviders(response.data.count || 0);
            }
          })
          .catch((error) => {
            console.error('Error fetching users:', error);
          });
      }, []);

 return (
     <>
       {isLoading ? (
         <SkeletonEarningCard />
       ) : (
         <MainCard
           border={false}
           content={false}
           sx={{
             bgcolor: 'warning.light',
             color: '#fff',
             overflow: 'hidden',
             position: 'relative',
             '&:after': {
               content: '""',
               position: 'absolute',
               width: 210,
               height: 210,
               background: `linear-gradient(210.04deg, ${theme.palette.warning.dark} -50.94%, rgba(144, 202, 249, 0) 83.49%)`,
               borderRadius: '50%',
               top: { xs: -105, sm: -85 },
               right: { xs: -140, sm: -95 }
             },
             '&:before': {
               content: '""',
               position: 'absolute',
               width: 210,
               height: 210,
               background: `linear-gradient(140.9deg, ${theme.palette.warning.dark} -14.02%, rgba(144, 202, 249, 0) 70.50%)`,
               borderRadius: '50%',
               top: { xs: -155, sm: -125 },
               right: { xs: -70, sm: -15 },
               opacity: 0.5
             }
           }}
         >
           <Box sx={{ p: 2.25 }}>
             <Grid container direction="column">
               <Grid item>
                 <Grid container justifyContent="space-between">
                   <Grid item>
                      <Avatar
                         variant="rounded"
                         sx={{
                           ...theme.typography.commonAvatar,
                           ...theme.typography.largeAvatar,
                           bgcolor: 'primary.800',
                           color: '#fff',
                           mt: 1
                         }}
                       >
                         <GroupIcon fontSize="inherit" />
                       </Avatar>
                   </Grid>
                 </Grid>
               </Grid>
               <Grid item>
                 <Grid container alignItems="center">
                   <Grid item>
                     <Typography sx={{ fontSize: '2.125rem', fontWeight: 500, mr: 1, mt: 1.75, mb: 0.75, color: '#000', }}>{totalProviders}</Typography>
                   </Grid>
                 </Grid>
               </Grid>
               <Grid item sx={{ mb: 1.25 }}>
                 <Typography
                   sx={{
                     fontSize: '1rem',
                     fontWeight: 500,
                     color: 'secondary.200'
                   }}
                 >
                   Total Providers
                 </Typography>
               </Grid>
             </Grid>
           </Box>
         </MainCard>
       )}
     </>
   );
 };

TotalProvidersCard.propTypes = {
   isLoading: PropTypes.bool
};

export default TotalProvidersCard;
