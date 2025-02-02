// material-ui
import { useTheme } from '@mui/material/styles';

import logo from 'assets/images/emerson.png';

// ==============================|| LOGO SVG ||============================== //

const Logo = () => {
  const theme = useTheme();

  return (
   <img src={logo} alt="Logo" width="50" height="50" />
  );
};

export default Logo;
