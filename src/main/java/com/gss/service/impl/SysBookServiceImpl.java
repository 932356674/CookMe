package com.gss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gss.dto.BookDTO;
import com.gss.dto.CookbookDTO;
import com.gss.entity.*;
import com.gss.mapper.*;
import com.gss.service.SysBookService;
import com.gss.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖镇楼                  BUG辟易
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 *
 * @Description:
 * @Company:
 * @Author: jzx
 * @Date: 2019/4/11 0011
 * @Time: 下午 14:21
 */
@Service
public class SysBookServiceImpl implements SysBookService {

    @Resource
    private CookbookMapper cookbookMapper;
    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private StepMapper stepMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CookbookTypeMapper cookbookTypeMapper;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private BookCommentMapper bookCommentMapper;
    @Resource
    private BooktypeMapper booktypeMapper;

    @Override
    public R add(BookDTO bookDTO) {
        try{
            bookDTO.setUsId(ShiroUtils.getUserId());
            bookDTO.setBookTime(new Date());
            int i = cookbookMapper.insert(bookDTO);
            List<Material> materials = bookDTO.getMaterial();
            for (Material material : materials) {
                materialMapper.insert(material);
            }
            List<Step> steps = bookDTO.getMethod();
            for (Step step : steps) {
                stepMapper.insert(step);
            }
            User u = userMapper.selectByPrimaryKey(ShiroUtils.getUserId());
            u.setUsBookcount(u.getUsBookcount()+1);
            userMapper.updateByPrimaryKey(u);
            List<Booktype> types = bookDTO.getTypes();
            for (Booktype type : types) {
                CookbookType cookbookType = new CookbookType();
                cookbookType.setTypeId(type.getTypeId());
                cookbookType.setBookId(bookDTO.getBookId());
                cookbookTypeMapper.insert(cookbookType);
            }
        }catch(Exception e){
            e.printStackTrace();
            return R.error("新增失败！");
        }
        return R.ok("新增成功！");
    }

    @Override
    public R addCollect(int bookId) {
        Collect collect = new Collect();
        collect.setUsId(ShiroUtils.getUserId());
        collect.setBookId(bookId);
        int i = collectMapper.insert(collect);
        if (i>0){
            return R.ok("收藏成功！");
        }else{
            return R.error("收藏失败！");
        }
    }

    @Override
    public ResultData selectBook(Pager pager, String search) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        CookbookExample cookbookExample = null;
        MaterialExample materialExample = null;
        if(StringUtils.isNotEmpty(search)){
            cookbookExample = new CookbookExample();
            CookbookExample.Criteria criteria = cookbookExample.createCriteria();
            criteria.andBookNameLike("%"+search+"%");

            materialExample = new MaterialExample();
            MaterialExample.Criteria criteria1 = materialExample.createCriteria();
            criteria1.andMatNameLike("%"+search+"%");
        }
        List<Cookbook> list = cookbookMapper.selectByExample(cookbookExample);
        List<Material> list1 = materialMapper.selectByExample(materialExample);
        List<Integer> list2=new ArrayList<>();
        for (Cookbook cookbook : list) {
            list2.add(cookbook.getBookId());
        }
        for (Material material : list1) {
            if (!list2.contains(material.getBookId())){
                list.add(cookbookMapper.selectByPrimaryKey(material.getBookId()));
            }
        }
        PageInfo info = new PageInfo(list);
        return new ResultData(info.getTotal(),info.getList());
    }

    @Override
    public R comment(int bookId, String commentValue) {

        BookComment bookComment = new BookComment();
        bookComment.setBookId(bookId);
        bookComment.setCommentTime(new Date());
        bookComment.setCommentValue(commentValue);
        bookComment.setUsId(ShiroUtils.getUserId());
        int i = bookCommentMapper.insert(bookComment);
        if (i>0){
            return R.ok("评论成功！");
        }else{
            return R.error("评论失败！");
        }
    }

    @Override
    public CookbookDTO selectBookById(int bookId) {
        CookbookDTO cookbookDTO = new CookbookDTO();

        Cookbook cookbook = cookbookMapper.selectByPrimaryKey(bookId);

        CookbookTypeExample example = new CookbookTypeExample();
        CookbookTypeExample.Criteria criteria= example.createCriteria();
        criteria.andBookIdEqualTo(bookId);
        List<CookbookType> bt = cookbookTypeMapper.selectByExample(example);

        List<Booktype> booktypes = null;
        for (CookbookType cookbookType : bt) {
            int i = cookbookType.getTypeId();
            Booktype booktype = booktypeMapper.selectByPrimaryKey(i);
            booktypes.add(booktype);
        }
        cookbookDTO = (CookbookDTO) cookbook;
        cookbookDTO.setTypes(booktypes);

        StepExample stepExample = new StepExample();
        StepExample.Criteria stepcriteria = stepExample.createCriteria();
        stepcriteria.andBookIdEqualTo(bookId);
        List<Step> stepList = stepMapper.selectByExample(stepExample);

        MaterialExample materialExample = new MaterialExample();
        MaterialExample.Criteria criteria1 = materialExample.createCriteria();
        criteria1.andBookIdEqualTo(bookId);
        List<Material> materials = materialMapper.selectByExample(materialExample);
        cookbookDTO.setMaterial(materials);
        cookbookDTO.setMethod(stepList);

        return cookbookDTO;
    }


}
