package ai.wiseloong.open.app.service.impl;

import ai.wiseloong.attachment.manage.apis.pojo.UploadFile;
import ai.wiseloong.attachment.manage.apis.service.IUploadFileService;
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
import ai.wiseloong.open.app.mapper.TOpenAppExtendMapper;
import ai.wiseloong.open.app.pojo.TOpenAppExtend;
import ai.wiseloong.open.app.pojo.dto.TOpenAppExtendDto;
import ai.wiseloong.open.app.pojo.vo.TOpenAppExtendVo;
import ai.wiseloong.open.app.service.ITOpenAppExtendService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * @ClassName: TOpenAppExtendService
 * @Description:  开发平台-应用扩展-ServiceImpl层
 * @author 张小平
 */
@Service
public class TOpenAppExtendServiceImpl implements ITOpenAppExtendService {

    @Resource
    private TOpenAppExtendMapper tOpenAppExtendMapper;




    @DubboReference(check = false)
    private IUploadFileService iuploadFileService;


    @Override
    public IPage<TOpenAppExtendVo> query(QueryCondition<TOpenAppExtendDto> queryCondition,int pageNum,int pageSize) {
        QueryWrapper<TOpenAppExtendDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition,false);
        Page<TOpenAppExtendVo> page  = new Page<>(pageNum,pageSize);
        page.setOrders(queryCondition.getOrderItemList());
        IPage<TOpenAppExtendVo> queryPageList = tOpenAppExtendMapper.query(page,wrapper);

        return queryPageList;
    }

    @Override
    @Transactional
    public ResultInfo<?> delete(List<String> idList) {
        List<TOpenAppExtend> tOpenAppExtends = tOpenAppExtendMapper.selectBatchIds(idList);
        if(!tOpenAppExtends.isEmpty()){
            tOpenAppExtendMapper.deleteBatchIds(idList);
        }

	    return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage());
    }

    @Override
    @Transactional
    public void saveOrUpdate(TOpenAppExtend tOpenAppExtend) {

        ValidatorUtils._validBean(tOpenAppExtend);

        LambdaQueryWrapper<TOpenAppExtend> wrapper = null;


        if(!StringUtils.hasText(tOpenAppExtend.getId())){
            tOpenAppExtendMapper.insert(tOpenAppExtend);
        }else{

            tOpenAppExtendMapper.updateById(tOpenAppExtend);
        }
    }

    @Override
    public TOpenAppExtend get(String id) {
        return tOpenAppExtendMapper.selectById(id);
    }

    @Override
    public List<TOpenAppExtendVo> selectList(TOpenAppExtendDto tOpenAppExtendDto) {
        QueryCondition<TOpenAppExtendDto> queryCondition = new QueryCondition<>();
        queryCondition.setEntity(tOpenAppExtendDto);
        QueryWrapper<TOpenAppExtendDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition, false);
        return tOpenAppExtendMapper.queryList(wrapper);
    }

    @Override
    public List<TOpenAppExtendVo> queryList(QueryCondition<TOpenAppExtendDto> queryCondition) {
        QueryWrapper<TOpenAppExtendDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition, true);
        return tOpenAppExtendMapper.queryList(wrapper);
    }

    @Override
    public void dataExport(HttpServletResponse res, QueryCondition<TOpenAppExtendDto> queryCondition) {
        try {
            ExcelOperation entity = new ExcelOperation(ExcelConstant.EXCEL.COLLEGE_EXCEL_EXPORT_DATA.getCode());
            CellStyle style = entity.getCellStyle(0, 0, 0);

            QueryWrapper<TOpenAppExtendDto> wrapper = QueryWrapperUtils.initQueryWrapper(queryCondition, true);
            List<Map<String, Object>> xxList = tOpenAppExtendMapper.list(wrapper);

            //excesl列表头部
            String[] colNamesStrArr = new String[]{"应用Id","浏览次数","使用次数","收藏次数","平均评分","扩展json",};
            //数据库对应字段
            String[] colCodesStrArr = new String[]{"appId","llcs","sycs","sccs","pjfs","kzdx",};
            //文件名
            String title = "开发平台-应用扩展_".concat(Utils.getTimeNow(Utils.DateFormatEnum.YMDHMS.getFormate())).concat(ExcelConstant.EXCEL.COLLEGE_EXCEL_FINAL_POSION.getCode());
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
            DataFormat dataFormat = wb.createDataFormat();// 设置可编辑列为文本格式,打开excel编辑不会自动科学计数法
            CellStyle style = wb.createCellStyle();
            style.setDataFormat(dataFormat.getFormat("@"));
            style.setAlignment(HorizontalAlignment.CENTER);//水平居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
            style.setWrapText(true);

            CellStyle style_1 = wb.createCellStyle();
            style_1.cloneStyleFrom(style);
            style_1.setBorderBottom(BorderStyle.THIN);
            style_1.setBorderLeft(BorderStyle.THIN);
            style_1.setBorderRight(BorderStyle.THIN);
            style_1.setBorderTop(BorderStyle.THIN);

            //获取当前登录用户ID
            LoginInfo loginInfo = WebUtils.getLoginInfoFromRequest(request);
            String userId = loginInfo.getSubject().get("id").toString();


            List<String[]> list = excellReader.readAllExcelContent(sheetIndex);
            if(list.size()==1)return  ResultInfoBuilder.failure(RestResultCode.CODE_500.getCode(),"模板没有数据,请重新导入");





            int lastIndex = list.get(0).length;
            List<TOpenAppExtend> rowList = new ArrayList<>();
            for(int i=list.size()-1;i>=1;i--){
                String[] arr = list.get(i);
                TOpenAppExtend tOpenAppExtend = TOpenAppExtend.builder().id(Utils.generateNewUUID()).appId(arr[0]).llcs(UtilsCommon.isNumber(arr[1])?Long.valueOf(arr[1]):null).sycs(UtilsCommon.isNumber(arr[2])?Long.valueOf(arr[2]):null).sccs(UtilsCommon.isNumber(arr[3])?Long.valueOf(arr[3]):null).pjfs(UtilsCommon.isNumber(arr[4])?Double.valueOf(arr[4]):null).kzdx(arr[5]).createId(userId).createTime(new Date()).build();



                String errorStr = ValidatorUtils.validBean(tOpenAppExtend);
                if(StringUtils.hasText(errorStr)){
                    excellReader.setCellValue(sheetIndex, i, lastIndex, errorStr, style);
                    continue;
                }


                excellReader.removeRow(i);
                rowList.add(tOpenAppExtend);
            }

            if(!rowList.isEmpty()){
                tOpenAppExtendMapper.batchInsert(rowList);
            }
            int num = list.size() - 1 - rowList.size();
            if(num>0){
                excellReader.setCellValue(sheetIndex, 0, lastIndex, "错误信息", style_1);
                UploadFile uploadFile = iuploadFileService.uploadByBytes(excellReader.getByteArray(),excellReader.getUploadFile("开发平台-应用扩展_错误Excel文件.xlsx"),loginInfo);
                return ResultInfoBuilder.failure(999,"导入成功:" + rowList.size() + "条，失败：" + num + "条，失败原因详情请查看Excel。",uploadFile.getFileUniqueCode(),null);
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
            ExcelOperation entity = new ExcelOperation("/t_open_app_extend_template.xlsx");


            entity.print(res,"开发平台-应用扩展_导入模板".concat(Utils.getTimeNow(Utils.DateFormatEnum.YMDHMS.getFormate())).concat(ExcelConstant.EXCEL.COLLEGE_EXCEL_FINAL_POSION.getCode()));
        }catch (Exception e){
            e.printStackTrace();
            throw new FighterRuntimeException(RestResultCode.CODE_500.getMessage(), RestResultCode.CODE_500.getCode(),false);
        }
    }
}