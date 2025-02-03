import{r as c,l as _,m as z,s as D,_ as f,n as N,o as A,j as e,p as I,q as W,a6 as S,a7 as ue,x as P,a8 as ge,N as he,P as X,T as i,S as xe,d as w,Q as V,R as Q,e as E,a9 as fe,h as q,aa as L,a2 as F,B as Z}from"./index-Ci-3xvsk.js";const re=c.createContext();function be(t){return _("MuiTable",t)}z("MuiTable",["root","stickyHeader"]);const ve=["className","component","padding","size","stickyHeader"],me=t=>{const{classes:o,stickyHeader:s}=t;return W({root:["root",s&&"stickyHeader"]},be,o)},ye=D("table",{name:"MuiTable",slot:"Root",overridesResolver:(t,o)=>{const{ownerState:s}=t;return[o.root,s.stickyHeader&&o.stickyHeader]}})(({theme:t,ownerState:o})=>f({display:"table",width:"100%",borderCollapse:"collapse",borderSpacing:0,"& caption":f({},t.typography.body2,{padding:t.spacing(2),color:(t.vars||t).palette.text.secondary,textAlign:"left",captionSide:"bottom"})},o.stickyHeader&&{borderCollapse:"separate"})),ee="table",je=c.forwardRef(function(o,s){const n=N({props:o,name:"MuiTable"}),{className:u,component:r=ee,padding:l="normal",size:d="medium",stickyHeader:g=!1}=n,b=A(n,ve),x=f({},n,{component:r,padding:l,size:d,stickyHeader:g}),j=me(x),h=c.useMemo(()=>({padding:l,size:d,stickyHeader:g}),[l,d,g]);return e.jsx(re.Provider,{value:h,children:e.jsx(ye,f({as:r,role:r===ee?null:"table",ref:s,className:I(j.root,u),ownerState:x},b))})}),U=c.createContext();function Ce(t){return _("MuiTableBody",t)}z("MuiTableBody",["root"]);const Te=["className","component"],Re=t=>{const{classes:o}=t;return W({root:["root"]},Ce,o)},ke=D("tbody",{name:"MuiTableBody",slot:"Root",overridesResolver:(t,o)=>o.root})({display:"table-row-group"}),Me={variant:"body"},te="tbody",$e=c.forwardRef(function(o,s){const n=N({props:o,name:"MuiTableBody"}),{className:u,component:r=te}=n,l=A(n,Te),d=f({},n,{component:r}),g=Re(d);return e.jsx(U.Provider,{value:Me,children:e.jsx(ke,f({className:I(g.root,u),as:r,ref:s,role:r===te?null:"rowgroup",ownerState:d},l))})});function we(t){return _("MuiTableCell",t)}const Se=z("MuiTableCell",["root","head","body","footer","sizeSmall","sizeMedium","paddingCheckbox","paddingNone","alignLeft","alignCenter","alignRight","alignJustify","stickyHeader"]),He=["align","className","component","padding","scope","size","sortDirection","variant"],_e=t=>{const{classes:o,variant:s,align:n,padding:u,size:r,stickyHeader:l}=t,d={root:["root",s,l&&"stickyHeader",n!=="inherit"&&`align${S(n)}`,u!=="normal"&&`padding${S(u)}`,`size${S(r)}`]};return W(d,we,o)},ze=D("td",{name:"MuiTableCell",slot:"Root",overridesResolver:(t,o)=>{const{ownerState:s}=t;return[o.root,o[s.variant],o[`size${S(s.size)}`],s.padding!=="normal"&&o[`padding${S(s.padding)}`],s.align!=="inherit"&&o[`align${S(s.align)}`],s.stickyHeader&&o.stickyHeader]}})(({theme:t,ownerState:o})=>f({},t.typography.body2,{display:"table-cell",verticalAlign:"inherit",borderBottom:t.vars?`1px solid ${t.vars.palette.TableCell.border}`:`1px solid
    ${t.palette.mode==="light"?ue(P(t.palette.divider,1),.88):ge(P(t.palette.divider,1),.68)}`,textAlign:"left",padding:16},o.variant==="head"&&{color:(t.vars||t).palette.text.primary,lineHeight:t.typography.pxToRem(24),fontWeight:t.typography.fontWeightMedium},o.variant==="body"&&{color:(t.vars||t).palette.text.primary},o.variant==="footer"&&{color:(t.vars||t).palette.text.secondary,lineHeight:t.typography.pxToRem(21),fontSize:t.typography.pxToRem(12)},o.size==="small"&&{padding:"6px 16px",[`&.${Se.paddingCheckbox}`]:{width:24,padding:"0 12px 0 16px","& > *":{padding:0}}},o.padding==="checkbox"&&{width:48,padding:"0 0 0 4px"},o.padding==="none"&&{padding:0},o.align==="left"&&{textAlign:"left"},o.align==="center"&&{textAlign:"center"},o.align==="right"&&{textAlign:"right",flexDirection:"row-reverse"},o.align==="justify"&&{textAlign:"justify"},o.stickyHeader&&{position:"sticky",top:0,zIndex:2,backgroundColor:(t.vars||t).palette.background.default})),p=c.forwardRef(function(o,s){const n=N({props:o,name:"MuiTableCell"}),{align:u="inherit",className:r,component:l,padding:d,scope:g,size:b,sortDirection:x,variant:j}=n,h=A(n,He),v=c.useContext(re),m=c.useContext(U),C=m&&m.variant==="head";let T;l?T=l:T=C?"th":"td";let M=g;T==="td"?M=void 0:!M&&C&&(M="col");const $=j||m&&m.variant,R=f({},n,{align:u,component:T,padding:d||(v&&v.padding?v.padding:"normal"),size:b||(v&&v.size?v.size:"medium"),sortDirection:x,stickyHeader:$==="head"&&v&&v.stickyHeader,variant:$}),O=_e(R);let J=null;return x&&(J=x==="asc"?"ascending":"descending"),e.jsx(ze,f({as:T,ref:s,className:I(O.root,r),"aria-sort":J,scope:M,ownerState:R},h))});function De(t){return _("MuiTableHead",t)}z("MuiTableHead",["root"]);const Ne=["className","component"],Ae=t=>{const{classes:o}=t;return W({root:["root"]},De,o)},Ie=D("thead",{name:"MuiTableHead",slot:"Root",overridesResolver:(t,o)=>o.root})({display:"table-header-group"}),We={variant:"head"},oe="thead",Je=c.forwardRef(function(o,s){const n=N({props:o,name:"MuiTableHead"}),{className:u,component:r=oe}=n,l=A(n,Ne),d=f({},n,{component:r}),g=Ae(d);return e.jsx(U.Provider,{value:We,children:e.jsx(Ie,f({as:r,className:I(g.root,u),ref:s,role:r===oe?null:"rowgroup",ownerState:d},l))})});function Be(t){return _("MuiTableRow",t)}const ae=z("MuiTableRow",["root","selected","hover","head","footer"]),Pe=["className","component","hover","selected"],Ue=t=>{const{classes:o,selected:s,hover:n,head:u,footer:r}=t;return W({root:["root",s&&"selected",n&&"hover",u&&"head",r&&"footer"]},Be,o)},Oe=D("tr",{name:"MuiTableRow",slot:"Root",overridesResolver:(t,o)=>{const{ownerState:s}=t;return[o.root,s.head&&o.head,s.footer&&o.footer]}})(({theme:t})=>({color:"inherit",display:"table-row",verticalAlign:"middle",outline:0,[`&.${ae.hover}:hover`]:{backgroundColor:(t.vars||t).palette.action.hover},[`&.${ae.selected}`]:{backgroundColor:t.vars?`rgba(${t.vars.palette.primary.mainChannel} / ${t.vars.palette.action.selectedOpacity})`:P(t.palette.primary.main,t.palette.action.selectedOpacity),"&:hover":{backgroundColor:t.vars?`rgba(${t.vars.palette.primary.mainChannel} / calc(${t.vars.palette.action.selectedOpacity} + ${t.vars.palette.action.hoverOpacity}))`:P(t.palette.primary.main,t.palette.action.selectedOpacity+t.palette.action.hoverOpacity)}}})),se="tr",ne=c.forwardRef(function(o,s){const n=N({props:o,name:"MuiTableRow"}),{className:u,component:r=se,hover:l=!1,selected:d=!1}=n,g=A(n,Pe),b=c.useContext(U),x=f({},n,{component:r,hover:l,selected:d,head:b&&b.variant==="head",footer:b&&b.variant==="footer"}),j=Ue(x);return e.jsx(Oe,f({as:r,ref:s,className:I(j.root,u),role:r===se?null:"row",ownerState:x},g))}),Ee=({title:t,subtitle:o,children:s,action:n,footer:u,cardheading:r,headtitle:l,headsubtitle:d,middlecontent:g})=>e.jsxs(he,{sx:{padding:0},elevation:9,variant:void 0,children:[r?e.jsxs(X,{children:[e.jsx(i,{variant:"h5",children:l}),e.jsx(i,{variant:"subtitle2",color:"textSecondary",children:d})]}):e.jsxs(X,{sx:{p:"30px"},children:[t&&e.jsxs(xe,{direction:"row",spacing:2,justifyContent:"space-between",alignItems:"center",mb:3,children:[e.jsxs(w,{children:[t&&e.jsx(i,{variant:"h5",children:t}),o&&e.jsx(i,{variant:"subtitle2",color:"textSecondary",children:o})]}),n]}),s]}),g,u]});var Y={},qe=Q;Object.defineProperty(Y,"__esModule",{value:!0});var le=Y.default=void 0,Le=qe(V()),Fe=e;le=Y.default=(0,Le.default)((0,Fe.jsx)("path",{d:"M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"}),"Close");var G={},Ve=Q;Object.defineProperty(G,"__esModule",{value:!0});var ie=G.default=void 0,Qe=Ve(V()),Ye=e;ie=G.default=(0,Qe.default)((0,Ye.jsx)("path",{d:"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6zM19 4h-3.5l-1-1h-5l-1 1H5v2h14z"}),"Delete");var K={},Ge=Q;Object.defineProperty(K,"__esModule",{value:!0});var ce=K.default=void 0,Ke=Ge(V()),Xe=e;ce=K.default=(0,Ke.default)((0,Xe.jsx)("path",{d:"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34a.9959.9959 0 0 0-1.41 0l-1.83 1.83 3.75 3.75z"}),"Edit");const Ze=(t,o=10)=>t.length>o?`${t.substring(0,o)}...`:t,tt=()=>{const[t,o]=c.useState([]),[s,n]=c.useState(!0),[u,r]=c.useState(!1),[l,d]=c.useState(null),[g,b]=c.useState(!1),[x,j]=c.useState(null),[h,v]=c.useState({title:"",description:"",deadline:"",posterImage:null}),[m,C]=c.useState({open:!1,message:"",isSuccess:!1});c.useEffect(()=>{T()},[]);const T=async()=>{try{const a=localStorage.getItem("token");if(!a){console.error("No token found"),n(!1);return}const y=(await E.get("/api/jobs/all",{headers:{Authorization:`Bearer ${a}`}})).data.sort((k,H)=>H.id-k.id);o(y)}catch(a){console.error("Error fetching jobs:",a)}finally{n(!1)}},M=a=>{d(a),v({title:a.title,description:a.description,deadline:a.deadline,posterImage:null}),r(!0)},$=()=>{r(!1),d(null)},R=a=>{a.target.name==="posterImage"?v({...h,posterImage:a.target.files[0]}):v({...h,[a.target.name]:a.target.value})},O=async()=>{if(!l)return;const a=localStorage.getItem("token");if(!a){console.error("No token found");return}const B=`/api/jobs/update/${l.id}`,y=new FormData;y.append("title",h.title),y.append("description",h.description),y.append("deadline",h.deadline),h.posterImage&&y.append("posterImage",h.posterImage);try{await E.put(B,y,{headers:{Authorization:`Bearer ${a}`,"Content-Type":"multipart/form-data"}}),C({open:!0,message:"Job updated successfully!",isSuccess:!0}),o(k=>k.map(H=>H.id===l.id?{...H,...h}:H)),$()}catch(k){console.error("Error updating job:",k),C({open:!0,message:"Job update failed!",isSuccess:!1})}},J=async()=>{if(!x)return;const a=localStorage.getItem("token");if(!a){console.error("No token found");return}const B=`/api/jobs/delete/${x.id}`;try{await E.delete(B,{headers:{Authorization:`Bearer ${a}`}}),C({open:!0,message:"Job deleted successfully!",isSuccess:!0}),o(y=>y.filter(k=>k.id!==x.id)),b(!1),j(null)}catch(y){console.error("Error deleting job:",y),C({open:!0,message:"Job deletion failed!",isSuccess:!1})}},de=a=>{j(a),b(!0)},pe=()=>{b(!1),j(null)};return c.useEffect(()=>{if(m.open){const a=setTimeout(()=>{C({...m,open:!1})},3e3);return()=>clearTimeout(a)}},[m]),e.jsxs(Ee,{title:"All Jobs",children:[e.jsx(w,{sx:{overflow:"auto",width:{xs:"280px",sm:"auto"}},children:s?e.jsx(i,{variant:"subtitle1",children:"Loading..."}):t.length===0?e.jsx(i,{variant:"subtitle1",children:"No Jobs Posted Yet"}):e.jsxs(je,{"aria-label":"jobs table",sx:{whiteSpace:"nowrap",mt:2},children:[e.jsx(Je,{children:e.jsxs(ne,{children:[e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Title"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Description"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Type"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Location"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Rate"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Posted Date"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Deadline"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Applications"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Status"})}),e.jsx(p,{children:e.jsx(i,{fontWeight:600,children:"Action"})})]})}),e.jsx($e,{children:t.map(a=>e.jsxs(ne,{children:[e.jsx(p,{children:e.jsx(i,{fontWeight:500,children:a.title})}),e.jsx(p,{children:e.jsx(i,{variant:"body2",children:Ze(a.description)})}),e.jsx(p,{children:a.type}),e.jsx(p,{children:a.location}),e.jsx(p,{children:a.rate}),e.jsx(p,{children:a.postedDate}),e.jsx(p,{children:a.deadline}),e.jsx(p,{children:a.applicationCount}),e.jsx(p,{children:e.jsx(fe,{label:"Active",sx:{backgroundColor:"success.main",color:"#fff"}})}),e.jsxs(p,{children:[e.jsx(q,{onClick:()=>M(a),color:"primary",children:e.jsx(ce,{})}),e.jsx(q,{onClick:()=>de(a),color:"error",children:e.jsx(ie,{})})]})]},a.id))})]})}),e.jsx(L,{open:u,onClose:$,children:e.jsxs(w,{sx:{position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:400,bgcolor:"background.paper",boxShadow:24,p:4,borderRadius:"8px"},children:[e.jsxs(w,{display:"flex",justifyContent:"space-between",alignItems:"center",children:[e.jsx(i,{variant:"h6",children:"Update Job"}),e.jsx(q,{onClick:$,children:e.jsx(le,{})})]}),e.jsx(F,{label:"Title",fullWidth:!0,margin:"normal",name:"title",value:h.title,onChange:R}),e.jsx(F,{label:"Description",fullWidth:!0,margin:"normal",name:"description",value:h.description,onChange:R,multiline:!0,rows:3}),e.jsx(F,{label:"Deadline",fullWidth:!0,margin:"normal",name:"deadline",type:"date",value:h.deadline,onChange:R}),e.jsx("input",{type:"file",name:"posterImage",accept:"image/*",onChange:R,style:{marginTop:"10px"}}),e.jsx(Z,{variant:"contained",color:"primary",fullWidth:!0,onClick:O,sx:{mt:2},children:"Save Changes"})]})}),e.jsx(L,{open:m.open,onClose:()=>C({...m,open:!1}),children:e.jsx(w,{sx:{position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:300,bgcolor:"background.paper",p:4,textAlign:"center",borderRadius:"8px"},children:e.jsx(i,{variant:"h6",color:m.isSuccess?"green":"red",children:m.message})})}),e.jsx(L,{open:g,onClose:pe,children:e.jsxs(w,{sx:{position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:300,bgcolor:"background.paper",p:4,textAlign:"center",borderRadius:"8px"},children:[e.jsx(i,{variant:"h6",children:"Are you sure you want to delete?"}),e.jsx(Z,{onClick:J,color:"error",variant:"contained",sx:{mt:2},children:"Delete"})]})})]})};export{tt as default};
