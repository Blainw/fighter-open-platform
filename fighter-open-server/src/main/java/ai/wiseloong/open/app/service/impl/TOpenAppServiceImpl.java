package ai.wiseloong.open.app.service.impl;

import ai.wiseloong.attachment.manage.apis.pojo.UploadFile;
import ai.wiseloong.attachment.manage.apis.service.IUploadFileService;
import ai.wiseloong.dicmanage.pojo.vo.PubDmConstVo;
import ai.wiseloong.dicmanage.service.ITConstService;
import ai.wiseloong.extend.utils.RestResultCode;
import ai.wiseloong.extend.utils.UtilsCommon;
import ai.wiseloong.extend.utils.ValidatorUtils;
import ai.wiseloong.extend.utils.excel.ExcelConstant;
import ai.wiseloong.extend.utils.excel.ExcelOperation;
import ai.wiseloong.extend.utils.mybatis.QueryWrapperUtils;
import ai.wiseloong.fighter.core.exception.FighterRuntimeException;
import ai.wiseloong.fighter.core.pojo.LoginInfo;
import ai.wiseloong.fighter.core.pojo.ResultInfo;
import ai.wiseloong.fighter.core.util.ResultInfoBuilder;
import ai.wiseloong.fighter.core.util.Utils;
import ai.wiseloong.fighter.core.util.WebUtils;
import ai.wiseloong.fighter.db.pojo.QueryCondition;
import ai.wiseloong.open.app.mapper.TOpenAppMapper;
import ai.wiseloong.open.app.pojo.TOpenApp;
import ai.wiseloong.open.app.pojo.dto.TOpenAppDto;
import ai.wiseloong.open.app.pojo.vo.TOpenAppVo;
import ai.wiseloong.open.app.service.ITOpenAppService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * @ClassName: TOpenAppService
 * @Description:  ????????????-??????-ServiceImpl???
 * @author ?????????
 */
@Service
public class TOpenAppServiceImpl implements ITOpenAppService {

    @Resource
    private TOpenAppMapper tOpenAppMapper;

    @DubboReference(check = false)
    private ITConstService constService;


    @DubboReference(check = false)
    private IUploadFileService iuploadFileService;


    @Override
    public IPage<TOpenAppVo> query(QueryCondition<TOpenAppDto> queryCondition,int pageNum,int pageSize) {
        QueryWrapper<TOpenAppDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition,false);
        Page<TOpenAppVo> page  = new Page<>(pageNum,pageSize);
        page.setOrders(queryCondition.getOrderItemList());
        IPage<TOpenAppVo> queryPageList = tOpenAppMapper.query(page,wrapper);

        return queryPageList;
    }

    @Override
    @Transactional
    public ResultInfo<?> delete(List<String> idList) {
        List<TOpenApp> tOpenApps = tOpenAppMapper.selectBatchIds(idList);
        if(!tOpenApps.isEmpty()){
            tOpenAppMapper.deleteBatchIds(idList);
        }

	    return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage());
    }

    @Override
    @Transactional
    public void saveOrUpdate(TOpenApp tOpenApp) {

        ValidatorUtils._validBean(tOpenApp);

        LambdaQueryWrapper<TOpenApp> wrapper = null;


        if(!StringUtils.hasText(tOpenApp.getId())){
            tOpenAppMapper.insert(tOpenApp);
        }else{

            tOpenAppMapper.updateById(tOpenApp);
        }
    }

    @Override
    public TOpenApp get(String id) {
        return tOpenAppMapper.selectById(id);
    }

    @Override
    public List<TOpenAppVo> selectList(TOpenAppDto tOpenAppDto) {
        QueryCondition<TOpenAppDto> queryCondition = new QueryCondition<>();
        queryCondition.setEntity(tOpenAppDto);
        QueryWrapper<TOpenAppDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition, false);
        return tOpenAppMapper.queryList(wrapper);
    }

    @Override
    public List<TOpenAppVo> queryList(QueryCondition<TOpenAppDto> queryCondition) {
        QueryWrapper<TOpenAppDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition, true);
        return tOpenAppMapper.queryList(wrapper);
    }

    @Override
    public void dataExport(HttpServletResponse res, QueryCondition<TOpenAppDto> queryCondition) {
        try {
            ExcelOperation entity = new ExcelOperation(ExcelConstant.EXCEL.COLLEGE_EXCEL_EXPORT_DATA.getCode());

            QueryWrapper<TOpenAppDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition, true);
            List<Map<String, Object>> xxList = tOpenAppMapper.list(wrapper);
            if(!CollectionUtils.isEmpty(xxList)){

                Map<String,String> yyflMap = new HashMap<>();
                List<PubDmConstVo> yyflVos =  constService.listByCataCode("YYFL");
                if(!yyflVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:yyflVos){
                        yyflMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> ssflMap = new HashMap<>();
                List<PubDmConstVo> ssflVos =  constService.listByCataCode("APP_CLASSIFY");
                if(!ssflVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:ssflVos){
                        ssflMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> yylxMap = new HashMap<>();
                List<PubDmConstVo> yylxVos =  constService.listByCataCode("APP_TYPE");
                if(!yylxVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:yylxVos){
                        yylxMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> blfsMap = new HashMap<>();
                List<PubDmConstVo> blfsVos =  constService.listByCataCode("BLFS");
                if(!blfsVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:blfsVos){
                        blfsMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> sfqyMap = new HashMap<>();
                List<PubDmConstVo> sfqyVos =  constService.listByCataCode("SFQY");
                if(!sfqyVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:sfqyVos){
                        sfqyMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> jzfsMap = new HashMap<>();
                List<PubDmConstVo> jzfsVos =  constService.listByCataCode("JZFS");
                if(!jzfsVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:jzfsVos){
                        jzfsMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> isLoginMap = new HashMap<>();
                List<PubDmConstVo> isLoginVos =  constService.listByCataCode("SFQY");
                if(!isLoginVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:isLoginVos){
                        isLoginMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> syxsMap = new HashMap<>();
                List<PubDmConstVo> syxsVos =  constService.listByCataCode("SFQY");
                if(!syxsVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:syxsVos){
                        syxsMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }
                Map<String,String> sfxsjjMap = new HashMap<>();
                List<PubDmConstVo> sfxsjjVos =  constService.listByCataCode("SFQY");
                if(!sfxsjjVos.isEmpty()){
                    for(PubDmConstVo pubDmConst:sfxsjjVos){
                        sfxsjjMap.put(pubDmConst.getValue(),pubDmConst.getLabel());
                    }
                }

                for(Map<String, Object> map:xxList){
                    map.put("yyfl",yyflMap.get(map.get("yyfl")));
                    map.put("ssfl",ssflMap.get(map.get("ssfl")));
                    map.put("yylx",yylxMap.get(map.get("yylx")));
                    map.put("blfs",blfsMap.get(map.get("blfs")));
                    map.put("sfqy",sfqyMap.get(map.get("sfqy")));
                    map.put("jzfs",jzfsMap.get(map.get("jzfs")));
                    map.put("isLogin",isLoginMap.get(map.get("isLogin")));
                    map.put("syxs",syxsMap.get(map.get("syxs")));
                    map.put("sfxsjj",sfxsjjMap.get(map.get("sfxsjj")));
                }
            }

            //excesl????????????
            String[] colNamesStrArr = new String[]{"????????????","pc???url","?????????url","??????","????????????","????????????","????????????","????????????","????????????","?????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","??????","??????","??????????????????","????????????","????????????","???????????????Id","?????????id","??????id",};
            //?????????????????????
            String[] colCodesStrArr = new String[]{"mc","pcUrl","sjUrl","icons","yyfl","ssfl","yylx","blfs","ssbm","gjz","ywsx","bldd","zxdh","dkfs","sfqy","jzfs","isLogin","syxs","fztj","sort","bz","sfxsjj","yyjj","yyly","dsfyyId","zyzid","zyid",};
            //?????????
            String title = "????????????-??????_".concat(Utils.getTimeNow(Utils.DateFormatEnum.YMDHMS.getFormate())).concat(ExcelConstant.EXCEL.COLLEGE_EXCEL_FINAL_POSION.getCode());
            ExcelOperation.commExport(res,entity,title, xxList,Arrays.asList(colNamesStrArr), Arrays.asList(colCodesStrArr));
        }catch (Exception e){
            e.printStackTrace();
            throw new FighterRuntimeException(RestResultCode.CODE_500.getMessage(), RestResultCode.CODE_500.getCode(),false);
        }
    }

    @Override
    @Transactional
    public ResultInfo excelImport(MultipartFile file,HttpServletRequest request, HttpServletResponse res) {
        try{
            int sheetIndex = 0;
            ExcelOperation excellReader = new ExcelOperation(file.getInputStream());
            XSSFWorkbook wb = excellReader.getWb();
            DataFormat dataFormat = wb.createDataFormat();// ?????????????????????????????????,??????excel?????????????????????????????????
            CellStyle style = wb.createCellStyle();
            style.setDataFormat(dataFormat.getFormat("@"));
            style.setAlignment(HorizontalAlignment.CENTER);//????????????
            style.setVerticalAlignment(VerticalAlignment.CENTER);//????????????
            style.setWrapText(true);

            CellStyle style_1 = wb.createCellStyle();
            style_1.cloneStyleFrom(style);
            style_1.setBorderBottom(BorderStyle.THIN);
            style_1.setBorderLeft(BorderStyle.THIN);
            style_1.setBorderRight(BorderStyle.THIN);
            style_1.setBorderTop(BorderStyle.THIN);

            //????????????????????????ID
            LoginInfo loginInfo = WebUtils.getLoginInfoFromRequest(request);
            String userId = loginInfo.getSubject().get("id").toString();


            List<String[]> list = excellReader.readAllExcelContent(sheetIndex);
            if(list.size()==1)return  ResultInfoBuilder.failure(RestResultCode.CODE_500.getCode(),"??????????????????,???????????????");


            Map<String,String> yyflMap = new HashMap<>();
            List<PubDmConstVo> yyflVos =  constService.listByCataCode("YYFL");
            if(!yyflVos.isEmpty()){
                for(PubDmConstVo pubDmConst:yyflVos){
                    yyflMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> ssflMap = new HashMap<>();
            List<PubDmConstVo> ssflVos =  constService.listByCataCode("APP_CLASSIFY");
            if(!ssflVos.isEmpty()){
                for(PubDmConstVo pubDmConst:ssflVos){
                    ssflMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> yylxMap = new HashMap<>();
            List<PubDmConstVo> yylxVos =  constService.listByCataCode("APP_TYPE");
            if(!yylxVos.isEmpty()){
                for(PubDmConstVo pubDmConst:yylxVos){
                    yylxMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> blfsMap = new HashMap<>();
            List<PubDmConstVo> blfsVos =  constService.listByCataCode("BLFS");
            if(!blfsVos.isEmpty()){
                for(PubDmConstVo pubDmConst:blfsVos){
                    blfsMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> sfqyMap = new HashMap<>();
            List<PubDmConstVo> sfqyVos =  constService.listByCataCode("SFQY");
            if(!sfqyVos.isEmpty()){
                for(PubDmConstVo pubDmConst:sfqyVos){
                    sfqyMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> jzfsMap = new HashMap<>();
            List<PubDmConstVo> jzfsVos =  constService.listByCataCode("JZFS");
            if(!jzfsVos.isEmpty()){
                for(PubDmConstVo pubDmConst:jzfsVos){
                    jzfsMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> isLoginMap = new HashMap<>();
            List<PubDmConstVo> isLoginVos =  constService.listByCataCode("SFQY");
            if(!isLoginVos.isEmpty()){
                for(PubDmConstVo pubDmConst:isLoginVos){
                    isLoginMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> syxsMap = new HashMap<>();
            List<PubDmConstVo> syxsVos =  constService.listByCataCode("SFQY");
            if(!syxsVos.isEmpty()){
                for(PubDmConstVo pubDmConst:syxsVos){
                    syxsMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }
            Map<String,String> sfxsjjMap = new HashMap<>();
            List<PubDmConstVo> sfxsjjVos =  constService.listByCataCode("SFQY");
            if(!sfxsjjVos.isEmpty()){
                for(PubDmConstVo pubDmConst:sfxsjjVos){
                    sfxsjjMap.put(pubDmConst.getLabel(),pubDmConst.getValue());
                }
            }



            int lastIndex = list.get(0).length;
            List<TOpenApp> rowList = new ArrayList<>();
            for(int i=list.size()-1;i>=1;i--){
                String[] arr = list.get(i);
                TOpenApp tOpenApp = TOpenApp.builder().id(Utils.generateNewUUID()).mc(arr[0]).pcUrl(arr[1]).sjUrl(arr[2]).icons(arr[3]).yyfl(arr[4]).ssfl(arr[5]).yylx(arr[6]).blfs(arr[7]).ssbm(arr[8]).gjz(arr[9]).ywsx(arr[10]).bldd(arr[11]).zxdh(arr[12]).dkfs(arr[13]).sfqy(arr[14]).jzfs(arr[15]).isLogin(arr[16]).syxs(arr[17]).fztj(arr[18]).sort(UtilsCommon.isNumber(arr[19])?Integer.valueOf(arr[19]):null).bz(arr[20]).sfxsjj(arr[21]).yyjj(arr[22]).yyly(arr[23]).dsfyyId(arr[24]).zyzid(arr[25]).zyid(arr[26]).createId(userId).createTime(new Date()).build();

                if(yyflMap.get(tOpenApp.getYyfl()) != null){
                    tOpenApp.setYyfl(yyflMap.get(tOpenApp.getYyfl()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(ssflMap.get(tOpenApp.getSsfl()) != null){
                    tOpenApp.setSsfl(ssflMap.get(tOpenApp.getSsfl()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(yylxMap.get(tOpenApp.getYylx()) != null){
                    tOpenApp.setYylx(yylxMap.get(tOpenApp.getYylx()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(blfsMap.get(tOpenApp.getBlfs()) != null){
                    tOpenApp.setBlfs(blfsMap.get(tOpenApp.getBlfs()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(sfqyMap.get(tOpenApp.getSfqy()) != null){
                    tOpenApp.setSfqy(sfqyMap.get(tOpenApp.getSfqy()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(jzfsMap.get(tOpenApp.getJzfs()) != null){
                    tOpenApp.setJzfs(jzfsMap.get(tOpenApp.getJzfs()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(isLoginMap.get(tOpenApp.getIsLogin()) != null){
                    tOpenApp.setIsLogin(isLoginMap.get(tOpenApp.getIsLogin()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(syxsMap.get(tOpenApp.getSyxs()) != null){
                    tOpenApp.setSyxs(syxsMap.get(tOpenApp.getSyxs()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "?????????????????????????????????", style);
                    continue;
                }
                if(sfxsjjMap.get(tOpenApp.getSfxsjj()) != null){
                    tOpenApp.setSfxsjj(sfxsjjMap.get(tOpenApp.getSfxsjj()));
                }else{
                    //??????????????????????????????
                            excellReader.setCellValue(sheetIndex, i, lastIndex, "???????????????????????????????????????", style);
                    continue;
                }


                String errorStr = ValidatorUtils.validBean(tOpenApp);
                if(StringUtils.hasText(errorStr)){
                    excellReader.setCellValue(sheetIndex, i, lastIndex, errorStr, style);
                    continue;
                }


                excellReader.removeRow(i);
                rowList.add(tOpenApp);
            }

            if(!rowList.isEmpty()){
                tOpenAppMapper.batchInsert(rowList);
            }
            int num = list.size() - 1 - rowList.size();
            if(num>0){
                excellReader.setCellValue(sheetIndex, 0, lastIndex, "????????????", style_1);
                UploadFile uploadFile = iuploadFileService.uploadByBytes(excellReader.getByteArray(), excellReader.getUploadFile("????????????-??????_??????Excel??????.xlsx"),loginInfo);
                return ResultInfoBuilder.failure(999,"????????????:" + rowList.size() + "???????????????" + num + "?????????????????????????????????Excel???",uploadFile.getFileUniqueCode(),null);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new FighterRuntimeException(RestResultCode.CODE_500.getMessage(), RestResultCode.CODE_500.getCode(),false);
        }
        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage());
    }

    @Override
    public void templateExport(HttpServletResponse res) {
        try{
            ExcelOperation entity = new ExcelOperation("/t_open_app_template.xlsx");

            List<PubDmConstVo> yyflVos =  constService.listByCataCode("YYFL");
            if(!yyflVos.isEmpty()){
                String[] yyflArr = new String[yyflVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:yyflVos){
                    yyflArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(4, yyflArr);
            }
            List<PubDmConstVo> ssflVos =  constService.listByCataCode("APP_CLASSIFY");
            if(!ssflVos.isEmpty()){
                String[] ssflArr = new String[ssflVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:ssflVos){
                    ssflArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(5, ssflArr);
            }
            List<PubDmConstVo> yylxVos =  constService.listByCataCode("APP_TYPE");
            if(!yylxVos.isEmpty()){
                String[] yylxArr = new String[yylxVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:yylxVos){
                    yylxArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(6, yylxArr);
            }
            List<PubDmConstVo> blfsVos =  constService.listByCataCode("BLFS");
            if(!blfsVos.isEmpty()){
                String[] blfsArr = new String[blfsVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:blfsVos){
                    blfsArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(7, blfsArr);
            }
            List<PubDmConstVo> sfqyVos =  constService.listByCataCode("SFQY");
            if(!sfqyVos.isEmpty()){
                String[] sfqyArr = new String[sfqyVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:sfqyVos){
                    sfqyArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(14, sfqyArr);
            }
            List<PubDmConstVo> jzfsVos =  constService.listByCataCode("JZFS");
            if(!jzfsVos.isEmpty()){
                String[] jzfsArr = new String[jzfsVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:jzfsVos){
                    jzfsArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(15, jzfsArr);
            }
            List<PubDmConstVo> isLoginVos =  constService.listByCataCode("SFQY");
            if(!isLoginVos.isEmpty()){
                String[] isLoginArr = new String[isLoginVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:isLoginVos){
                    isLoginArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(16, isLoginArr);
            }
            List<PubDmConstVo> syxsVos =  constService.listByCataCode("SFQY");
            if(!syxsVos.isEmpty()){
                String[] syxsArr = new String[syxsVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:syxsVos){
                    syxsArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(17, syxsArr);
            }
            List<PubDmConstVo> sfxsjjVos =  constService.listByCataCode("SFQY");
            if(!sfxsjjVos.isEmpty()){
                String[] sfxsjjArr = new String[sfxsjjVos.size()];
                int index =0;
                for(PubDmConstVo pubDmConst:sfxsjjVos){
                    sfxsjjArr[index] = pubDmConst.getLabel();
                    index++;
                }

                entity.generateRangeList(21, sfxsjjArr);
            }

            entity.print(res,"????????????-??????_????????????".concat(Utils.getTimeNow(Utils.DateFormatEnum.YMDHMS.getFormate())).concat(ExcelConstant.EXCEL.COLLEGE_EXCEL_FINAL_POSION.getCode()));
        }catch (Exception e){
            e.printStackTrace();
            throw new FighterRuntimeException(RestResultCode.CODE_500.getMessage(), RestResultCode.CODE_500.getCode(),false);
        }
    }
}